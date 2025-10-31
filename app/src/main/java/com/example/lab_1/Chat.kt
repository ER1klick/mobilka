package com.example.lab_1

import java.io.Serializable

data class Chat(
    val senderName: String,
    val lastMessage: String,
    val timestamp: String,
    val profileImage: Int
)

