package com.example.lab_1

import java.io.Serializable

data class User(
    val name: String,
    val email: String,
    val password: String
) : Serializable