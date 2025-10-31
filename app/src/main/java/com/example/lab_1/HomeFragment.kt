package com.example.lab_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    companion object {
        private const val ARG_USER = "user"

        fun newInstance(user: User): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putSerializable(ARG_USER, user)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatRecyclerView = view.findViewById(R.id.chatRecyclerView)
        chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val chatList = listOf(
            Chat("1 сезон", "просмотров: 123123123", "12:01", R.drawable.profile_image_1),
            Chat("2 сезон", "просмотров: 12312312", "12:05", R.drawable.profile_image_2),
            Chat("3 сезон", "просмотров: 1231231", "12:10", R.drawable.profile_image_3),
            Chat("4 сезон", "просмотров: 123123", "12:40", R.drawable.profile_image_4),
            Chat("5 сезон", "просмотров: 12312", "19:23", R.drawable.profile_image_5),
            Chat("6 сезон", "просмотров: 1231", "11:15", R.drawable.profile_image_6),
            Chat("7 сезон", "просмотров: 123", "02:30", R.drawable.profile_image_7),
            Chat("8 сезон", "просмотров: 12", "15:32", R.drawable.profile_image_8),
        )
        chatAdapter = ChatAdapter(chatList)
        chatRecyclerView.adapter = chatAdapter
    }
}