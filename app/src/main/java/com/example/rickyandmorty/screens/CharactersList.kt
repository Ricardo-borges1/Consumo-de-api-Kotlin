package com.example.rickyandmorty.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.rickyandmorty.model.Character
import com.example.rickyandmorty.model.Episode
import com.example.rickyandmorty.model.Result
import com.example.rickyandmorty.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
@Composable
fun CharacterList(controleDeNavegacao: NavHostController?) {

    var characterList by remember {
        mutableStateOf(listOf<Character>())
    }

    val callCharacters = RetrofitFactory()
        .getCharactersService().getAllCharacters()

    callCharacters.enqueue(object : Callback<Result> {
        override fun onResponse(p0: Call<Result>, p1: Response<Result>) {
            characterList = p1.body()!!.results
        }

        override fun onFailure(p0: Call<Result>, p1: Throwable) {

        }

    })

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = Color.Gray
    ){
        Column (
            modifier = Modifier.padding(16.dp)
        ){
           Color.LightGray
            Spacer(modifier = Modifier.height(24.dp))
            LazyColumn {
                items(characterList){ char ->
                    CharacterCard(character = char, controleDeNavegacao)
                }
            }
        }
    }

}

@Composable
fun CharacterCard(character: Character?, controleDeNavegacao: NavHostController?){
    Card(
        modifier = Modifier
            .padding(bottom = 6.dp)
            .fillMaxWidth()
            .clickable (
                onClick = { controleDeNavegacao?.navigate("detalhePersonagem/${character?.id}") }
            )
            .height(100.dp),
        colors = CardDefaults
            .cardColors(
                containerColor = Color.Black
            )
    ) {
        Row (modifier = Modifier.fillMaxWidth()){
            Card (modifier = Modifier.size(100.dp)) {
                AsyncImage(
                    model = character?.image,
                    contentDescription = "",
                )
            }
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ){
                Text(text = character?.name!!,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(text = character.species,
                    color = Color.Yellow
                )
            }
        }
    }
}

@Preview
@Composable
private fun CharacterCardPreview(){
    CharacterCard(character = Character(), controleDeNavegacao = null)
}

@Preview(showSystemUi = true)
@Composable
private fun CharacterListPreview(){
    CharacterList(controleDeNavegacao = null)
}