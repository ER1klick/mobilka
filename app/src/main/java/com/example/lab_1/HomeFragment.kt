package com.example.lab_1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        binding.chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val chatList = createChatList()
        val chatAdapter = ChatAdapter(chatList) {
            val intent = Intent(requireContext(), ChatActivity::class.java)
            startActivity(intent)
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



