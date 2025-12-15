package com.example.lab_1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab_1.databinding.ChatItemBinding

class ChatAdapter(
    private var characters: List<Character>,
    private val onItemClicked: (Character) -> Unit
) : RecyclerView.Adapter<ChatAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position], onItemClicked)
    }

    override fun getItemCount() = characters.size

    fun updateData(newCharacters: List<Character>) {
        characters = newCharacters
        notifyDataSetChanged()
    }

    class CharacterViewHolder(private val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character, onItemClicked: (Character) -> Unit) {
            binding.senderNameTextView.text = character.name
            binding.lastMessageTextView.text = character.status
            binding.timestampTextView.text = ""

            Glide.with(binding.root.context)
                .load(character.imageUrl)
                .circleCrop()
                .into(binding.profileImageView)

            itemView.setOnClickListener {
                onItemClicked(character)
            }
        }
    }
}