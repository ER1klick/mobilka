package com.example.lab_1

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lab_1.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Разрешение получено", Toast.LENGTH_SHORT).show()
                updateBackupButtons(true)
            } else {
                Toast.makeText(requireContext(), "Разрешение не предоставлено", Toast.LENGTH_SHORT).show()
                updateBackupButtons(false)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupListeners()
        requestStoragePermission()
    }

    private fun observeViewModel() {
        viewModel.isDarkMode.observe(viewLifecycleOwner) { isDarkMode ->
            binding.themeSwitch.isChecked = isDarkMode
            applyTheme(isDarkMode)
        }
        viewModel.notificationsEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding.notificationsSwitch.isChecked = isEnabled
        }
        viewModel.backupState.observe(viewLifecycleOwner) { state ->
            binding.externalFileStatus.text = if (state.externalFileExists) "Существует (${state.externalFilePath})" else "Отсутствует"
            binding.internalFileStatus.text = if (state.internalFileExists) "Существует" else "Отсутствует"

            binding.deleteBackupButton.isEnabled = state.externalFileExists
            binding.restoreBackupButton.isEnabled = state.internalFileExists && !state.externalFileExists
        }
    }

    private fun setupListeners() {
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setTheme(isChecked)
        }
        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveNotificationsSetting(isChecked)
        }
        binding.createBackupButton.setOnClickListener {
            viewModel.createBackup(getBackupFileName())
        }
        binding.deleteBackupButton.setOnClickListener {
            viewModel.deleteBackup(getBackupFileName())
        }
        binding.restoreBackupButton.setOnClickListener {
            viewModel.restoreBackup(getBackupFileName())
        }
    }

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            updateBackupButtons(true)
        }
    }

    private fun updateBackupButtons(enabled: Boolean) {
        binding.createBackupButton.isEnabled = enabled
    }

    private fun getBackupFileName(): String {
        return binding.backupFileNameEditText.text.toString().ifEmpty { "601-650.txt" }
    }

    private fun applyTheme(isDarkMode: Boolean) {
        val mode = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}