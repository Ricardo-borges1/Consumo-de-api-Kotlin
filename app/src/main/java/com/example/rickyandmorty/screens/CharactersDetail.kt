package com.example.rickyandmorty.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.example.rickyandmorty.model.Character
import com.example.rickyandmorty.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
@Composable
fun <NavHostController> CharacterDetail(controleDeNavegacao: NavHostController, i: Int) {

    val id = remember {
        mutableStateOf(0)
    }
    id.value = i

    //List<Char>

    var character = remember {
        mutableStateOf(Character())
    }

    //Efetuar a chamada para a API
    val callCharacter = RetrofitFactory()
        .getCharactersService().getCharacterById(id.value)

    callCharacter.enqueue(object : Callback<Character> {
        override fun onResponse(p0: Call<Character>, p1: Response<Character>) {
            character.value = p1.body()!!
        }

        override fun onFailure(p0: Call<Character>, p1: Throwable) {
            TODO("Not yet implemented")
        }
    })


    Column {

        AsyncImage(
            model = character.value.image,
            contentDescription = "",
        )
        Text(text = character.value.name)
        Text(text = character.value.origin?.name ?: "")
        Text(text = character.value.species)
        Text(text = character.value.status)
        Text(text = character.value.gender)

    }
}