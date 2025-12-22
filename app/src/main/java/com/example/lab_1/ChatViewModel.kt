@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
package com.example.lab_1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi

class ChatViewModel : ViewModel() {

    private val _character = MutableLiveData<Character>()
    val character: LiveData<Character> get() = _character

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchCharacter(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCharacterById(id)
                _character.postValue(response)
            } catch (e: Exception) {
                _error.postValue("Failed to load character details: ${e.message}")
            }
        }
    }
}