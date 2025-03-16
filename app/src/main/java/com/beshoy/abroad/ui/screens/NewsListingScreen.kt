package com.beshoy.abroad.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.beshoy.abroad.data.domain.NewsObject
import com.beshoy.abroad.data.domain.NewsResponse
import com.beshoy.abroad.data.repo.ResourceState
import com.beshoy.abroad.ui.components.NewsItem
import com.beshoy.abroad.viewModel.NewsViewModel

@Composable
fun NewsListingScreen(
    navController: NavController,
    isSearch: Boolean = false,
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    val newsState = newsViewModel.newsState.collectAsStateWithLifecycle()
    LaunchedEffect(isSearch) {
        newsViewModel.getNews(if (!isSearch) "us" else "")
    }
    ShowNewsList(
        isSearch = isSearch,
        newsState = newsState.value,
        navController = navController,
        onSearchTextChanged = {
            newsViewModel.onSearchTextChanged(it)
        }
    )
}

@Composable
fun ShowNewsList(
    isSearch: Boolean,
    newsState: ResourceState,
    navController: NavController,
    onSearchTextChanged: (String) -> Unit
) {
    val searchText = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(8.dp)) {

            if (isSearch) {
                OutlinedTextField(
                    value = searchText.value,
                    onValueChange = {
                        searchText.value = it
                        onSearchTextChanged(
                            searchText.value
                        )
                    },
                    label = { Text("Search News") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            when (newsState) {
                is ResourceState.Loading -> {
                    if ((isSearch && searchText.value.length >= 3) || !isSearch) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = Color.Black.copy(alpha = 0.3f)
                        )
                    }
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
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "news",
                                    it
                                )
                                navController.navigate("newsDetail")
                            }
                        }
                    }
                }

                is ResourceState.Error -> {
                    CustomAlertDialog()
//                    Text("Error: ${newsList.message}")
                }
            }

        }
    }
}

@Composable
fun CustomAlertDialog() {
    val shouldShowDialog = remember { mutableStateOf(true) }

    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = "Warning", tint = Color.Red)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Error", fontWeight = FontWeight.Bold)
                }
            },
            text = { Text(text = "Please check internet connection and try again") },
            confirmButton = {
                OutlinedButton(onClick = {
                    shouldShowDialog.value = false
                }) {
                    Text("OK")
                }
            },
        )

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

