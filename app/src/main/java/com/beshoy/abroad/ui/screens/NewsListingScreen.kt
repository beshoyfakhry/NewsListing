package com.beshoy.abroad.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.beshoy.abroad.data.domain.NewsObject
import com.beshoy.abroad.data.domain.NewsResponse
import com.beshoy.abroad.data.repo.ResourceState
import com.beshoy.abroad.ui.components.CustomSearchAlertDialog
import com.beshoy.abroad.ui.components.NewsItem
import com.beshoy.abroad.viewModel.NewsViewModel

@Composable
fun NewsListingScreen(
    navController: NavController,
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    val newsState = newsViewModel.newsState.collectAsStateWithLifecycle()
    //Is is the wright way to do so ?
    var firstTime by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (firstTime) {
            firstTime = false
            newsViewModel.getNews("US")
        }
    }

    ShowNewsList(
        newsState = newsState.value,
        itemClicked = {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "news",
                it
            )
            navController.navigate(Screen.NewsDetails.route)
        }
    )
}

@Composable
fun ShowNewsList(
    newsState: ResourceState,
    itemClicked: (NewsObject) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            when (newsState) {
                is ResourceState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.Black.copy(alpha = 0.3f)
                    )
                }

                is ResourceState.Success<*> -> {
                    val articles = (newsState.data as NewsResponse).articles

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(articles)
                        { it ->
                            NewsItem(it)
                            {
                                itemClicked(it)
                            }
                        }
                    }
                }

                is ResourceState.Error -> {
                    CustomSearchAlertDialog("Please check internet connection and try again")

                }
            }
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
    val newsList = remember {
        mutableStateOf(mockNews)
    }
}
