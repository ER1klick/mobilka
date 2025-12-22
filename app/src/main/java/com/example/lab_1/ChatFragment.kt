@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
package com.example.lab_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.lab_1.databinding.FragmentChatBinding
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val args: ChatFragmentArgs by navArgs()
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.fetchCharacter(args.characterId)
    }

    private fun observeViewModel() {
        viewModel.character.observe(viewLifecycleOwner) { character ->
            binding.nameTextView.text = character.name
            binding.statusTextView.text = "Status: ${character.status}"
            binding.speciesTextView.text = "Species: ${character.species}"
            binding.genderTextView.text = "Gender: ${character.gender}"
            binding.originTextView.text = "Origin: ${character.origin.name}"
            binding.locationTextView.text = "Last known location: ${character.location.name}"

            Glide.with(this)
                .load(character.imageUrl)
                .into(binding.characterImageView)
        }
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}