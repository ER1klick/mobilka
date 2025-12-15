package com.example.lab_1

import retrofit2.http.GET

interface ApiService {
    @GET("api/character")
    suspend fun getCharacters(): CharacterResponse
}