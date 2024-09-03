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
fun CharactersDetail(modifier: Modifier = Modifier) {

    val characterList = remember {
        mutableStateOf(listOf<Character>())
    }
    //List<Char>

    val character = remember {
        mutableStateOf(Character())
    }

    val id = remember {
        mutableStateOf("")
    }

    //Efetuar a chamada para a API


    Column {

        OutlinedTextField(
            value = id.value,
            onValueChange = {id.value = it}
        )

        Button(onClick = {

            //Efetuar a chamada para a API
            val callCharacterById = RetrofitFactory()
                .getCharactersService()
                .getCharacterById(id.value.toInt())


            //p0: envio
            //p1: resposta


            callCharacterById.enqueue(object : Callback<Character> {
                override fun onResponse(p0: Call<Character>, p1: Response<Character>) {
                    character.value = p1.body()!!
                    Log.i(
                        "RICK_MORTY",
                        "${character.value.name} - ${character.value.origin!!.name}"
                    )
                }

                override fun onFailure(p0: Call<Character>, p1: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }) {
            Text(text = "Buscar")
        }


        AsyncImage(model = character.value.image, contentDescription = "" )

        Text(text = character.value.name)
        Text(text = character.value.origin?.name ?: "")
        Text(text = character.value.species)
        Text(text = character.value.status)
        Text(text = character.value.gender)

    }
}