@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
package com.example.lab_1

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/character/{ids}")
    suspend fun getCharacters(@Path("ids") ids: String): List<Character>

    @GET("api/character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character
}