package com.beshoy.abroad.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.beshoy.abroad.ui.components.NewsItem
import com.beshoy.abroad.viewModel.NewsViewModel

@Composable
fun NewsListingScreen(navController: NavController) {

    val viewModel: NewsViewModel = hiltViewModel()
    val isLoading = viewModel.isLoading.collectAsState()
    val newsList = viewModel.newsList.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    // .systemBarsPadding()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(newsList.value)
                { it ->
                    Log.d(
                        "data",
                        "the data are \n title is ${it.title}  \n desc is ${it.description}  \n content is ${it.content}  \n url is ${it.urlToImage} "
                    )
                    NewsItem(it)

                    {
                        navController.currentBackStackEntry?.savedStateHandle?.set("news", it)
                        navController.navigate("newsDetail")

                    }
                }
            }
        }
    }
}