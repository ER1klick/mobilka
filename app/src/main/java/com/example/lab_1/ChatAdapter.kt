package com.example.lab_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val chatList: List<Chat>,
    private val onItemClicked: (Chat) -> Unit
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImageView: ImageView = itemView.findViewById(R.id.profileImageView)
        val senderNameTextView: TextView = itemView.findViewById(R.id.senderNameTextView)
        val lastMessageTextView: TextView = itemView.findViewById(R.id.lastMessageTextView)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val currentChat = chatList[position]
        holder.profileImageView.setImageResource(currentChat.profileImage)
        holder.senderNameTextView.text = currentChat.senderName
        holder.lastMessageTextView.text = currentChat.lastMessage
        holder.timestampTextView.text = currentChat.timestamp
        holder.itemView.setOnClickListener {
            onItemClicked(currentChat)
        }
    }

    override fun getItemCount() = chatList.size
}