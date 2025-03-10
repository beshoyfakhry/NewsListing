package com.beshoy.abroad.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.beshoy.abroad.data.domain.NewsObject
import com.beshoy.abroad.ui.components.NewsItem
import com.beshoy.abroad.viewModel.NewsViewModel

@Composable
fun NewsListingScreen(navController: NavController, isSearch: Boolean = false) {

    val newsViewModel: NewsViewModel = hiltViewModel()
    val isLoading = newsViewModel.isLoading.collectAsState()
    val newsList = newsViewModel.newsList.collectAsState()
    if (!isSearch) {
        newsViewModel.getNews()
    }
    ShowNewsList(
        newsViewModel,
        isLoading = isLoading.value,
        isSearch = isSearch,
        newsList = newsList.value,
        navController = navController
    )
}

@Composable
fun ShowNewsList(
    newsViewModel: NewsViewModel,
    isLoading: Boolean,
    isSearch: Boolean,
    newsList: List<NewsObject>,
    navController: NavController
) {


    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black.copy(alpha = 0.3f)  // Semi-transparent black
            )
        }
//        else {
        Column(modifier = Modifier.padding(8.dp)) {
            if (isSearch) {

                OutlinedTextField(
                    value = newsViewModel.searchText.value,
                    onValueChange = { newsViewModel.onSearchTextChanged(it) },
                    label = { Text("Search News") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )


            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(newsList)
                { it ->
                    NewsItem(it)

                    {
                        navController.currentBackStackEntry?.savedStateHandle?.set("news", it)
                        navController.navigate("newsDetail")

                    }
                }
            }
//            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewNewsListingScreen() {
    val mockNewsList = listOf(
        NewsObject(
            title = "Sample News 1",
            description = "This is a sample news description",
            content = "Sample content...",
            urlToImage = "https://via.placeholder.com/150", author = "", publishedAt = ""
        ),
        NewsObject(
            title = "Sample News 2",
            description = "Another sample news description",
            content = "More sample content...",
            urlToImage = "https://via.placeholder.com/150", author = "", publishedAt = ""
        )
    )

    NewsListingScreenPreview(mockNewsList)
}

@Composable
fun NewsListingScreenPreview(mockNews: List<NewsObject>) {
    val navController = rememberNavController()
    //  val newsViewModel = NewsViewModel(null)
    val newsList = remember {
        mutableStateOf(mockNews)
    }

//    ShowNewsList(null,isLoading = false, true, newsList = newsList.value, navController)
}

