package com.example.lab_1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi

@OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
class HomeViewModel : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchCharacters() {
        viewModelScope.launch {
            try {
                val characterIds = (601..650).joinToString(",")
                val response = RetrofitInstance.api.getCharacters(characterIds)
                _characters.postValue(response)
            } catch (e: Exception) {
                _error.postValue("Failed to load data: ${e.message}")
            }
        }
    }
}