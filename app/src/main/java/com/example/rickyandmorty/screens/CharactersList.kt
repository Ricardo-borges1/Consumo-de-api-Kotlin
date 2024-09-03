package com.example.rickyandmorty.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rickyandmorty.model.Character
import com.example.rickyandmorty.model.Result
import com.example.rickyandmorty.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CharactersList(modifier: Modifier = Modifier){

    var characterList by remember {
        mutableStateOf(listOf<Character>())
    }

    val callCharacter = RetrofitFactory()
        .getCharactersService().getAllCharacters()

    callCharacter.enqueue(object : Callback<Result>{
        override fun onResponse(p0: Call<Result>, p1: Response<Result>) {
            characterList = p1.body()!!.results
        }

        override fun onFailure(p0: Call<Result>, p1: Throwable) {


        }

    } )

    Surface ( modifier = Modifier.fillMaxSize(),
        color = Color.Gray
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Rick and Morty")
            Spacer(modifier = Modifier.height(24.dp))
            LazyColumn {
                items(characterList){ it ->
                    CharacterCard(character = it)
                }
            }

        }
    }
}

@Composable
fun CharacterCard(character: Character?){
    Card (
        modifier = Modifier
            .padding(bottom = 6.dp)
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF000BCD4))
    ) {
        Row (
            modifier = Modifier.fillMaxSize()
        ){
           Card (
               modifier = Modifier.size(100.dp)
           ){
               AsyncImage(model = character?.image, contentDescription = "")
           }
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
            ){
                Text(
                    text = character?.name!!,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = character.species,
                    color = Color.White
                )
            }
        }
    }
}


@Preview
@Composable
private fun CharacterCardPreview(){
    CharacterCard(character = Character())
}




@Preview(showSystemUi = true)
@Composable
private fun CharactersListPreview(){
    CharactersList()
}