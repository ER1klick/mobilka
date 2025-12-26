package com.example.lab_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
<<<<<<< Updated upstream
import androidx.fragment.app.Fragment
=======
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
>>>>>>> Stashed changes
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab_1.databinding.ActivityHomeBinding

class HomeFragment : Fragment() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
<<<<<<< Updated upstream
=======
        setupToolbar()
        setupRecyclerView()
        observeViewModel()
        if (viewModel.characters.value.isNullOrEmpty()) {
            viewModel.fetchCharacters()
        }
    }

    private fun setupToolbar() {
        binding.toolbar.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }



            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return if (menuItem.itemId == R.id.action_settings) {
                    findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
                    true
                } else {
                    false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
>>>>>>> Stashed changes

        binding.chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val chatList = createChatList()
        val chatAdapter = ChatAdapter(chatList) { chat ->
            val action = HomeFragmentDirections.actionHomeFragmentToChatFragment(chat.senderName)
            findNavController().navigate(action)
        }
        binding.chatRecyclerView.adapter = chatAdapter
    }

    private fun createChatList(): List<Chat> {
        return listOf(
            Chat("1 сезон", "просмотров: 123123123", "12:01", R.drawable.profile_image_1),
            Chat("2 сезон", "просмотров: 12312312", "12:05", R.drawable.profile_image_2),
            Chat("3 сезон", "просмотров: 1231231", "12:10", R.drawable.profile_image_3),
            Chat("4 сезон", "просмотров: 123123", "12:40", R.drawable.profile_image_4),
            Chat("5 сезон", "просмотров: 12312", "19:23", R.drawable.profile_image_5),
            Chat("6 сезон", "просмотров: 1231", "11:15", R.drawable.profile_image_6),
            Chat("7 сезон", "просмотров: 123", "02:30", R.drawable.profile_image_7),
            Chat("8 сезон", "просмотров: 12", "15:32", R.drawable.profile_image_8),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}