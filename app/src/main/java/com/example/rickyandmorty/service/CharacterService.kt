package com.example.rickyandmorty.service

import com.example.rickyandmorty.model.Character
import com.example.rickyandmorty.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {

    //https://rickandmortyapi.com/api/character
    @GET("character")
    fun getAllCharacters(): Call<Result>

    @GET("character/{id}")
      fun getCharacterById( @Path("id") id: Int): Call<Character>
}