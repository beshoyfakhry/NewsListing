package com.beshoy.abroad.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.beshoy.abroad.R
import com.beshoy.abroad.data.domain.NewsObject
import com.beshoy.abroad.data.domain.NewsResponse
import com.beshoy.abroad.data.repo.ResourceState
import com.beshoy.abroad.ui.components.CustomSearchAlertDialog
import com.beshoy.abroad.ui.components.NewsItem
import com.beshoy.abroad.viewModel.NewsViewModel

@Composable
fun NewsSearchScreen(
  navController: NavController,
  newsViewModel: NewsViewModel = hiltViewModel()
) {

  val newsState = newsViewModel.newsState.collectAsStateWithLifecycle()
  val query by newsViewModel.queryState.collectAsStateWithLifecycle()

  Column {
    SearchBar(
      currentQuery = query,
      onTextChanged = newsViewModel::onSearchTextChanged
    )

    ShowSearchList(
      query,
      newsState = newsState.value,
      moveDetailsAction = {
        navController.currentBackStackEntry?.savedStateHandle?.set(
          "news",
          it
        )
        navController.navigate(Screen.NewsDetails.route)
      }
    )
  }
}

@Composable
fun SearchBar(
  currentQuery: String,
  onTextChanged: (String) -> Unit
) {
  OutlinedTextField(
    value = currentQuery,
    onValueChange = onTextChanged,
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp),
    label = { Text(stringResource(R.string.search)) },
    singleLine = true
  )
}

@Composable
fun ShowSearchList(
  query: String,
  newsState: ResourceState,
  moveDetailsAction: (NewsObject) -> Unit
) {
  if (query.isNotEmpty()) {
    val searchText = remember { mutableStateOf("") }
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.TopCenter
    ) {
      Column(modifier = Modifier.padding(8.dp)) {
        when (newsState) {
          is ResourceState.Loading -> {
            if (searchText.value.isNotEmpty()) {
              CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Black.copy(alpha = 0.3f)
              )
            }
          }

          is ResourceState.Success<*> -> {
            val articles = (newsState.data as NewsResponse).articles
            if (articles.isNotEmpty()) {
              val listState = rememberSaveable(saver = LazyListState.Saver) {
                LazyListState()
              }
              LazyColumn(
                state = listState,
                modifier = Modifier
                  .fillMaxSize()
                  .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                items(articles)
                { it ->
                  NewsItem(it)
                  {
                    moveDetailsAction(it)
                  }
                }
              }
            } else {
              CustomSearchAlertDialog("No Result For Your Search")
            }
          }

          is ResourceState.Error -> {
            CustomSearchAlertDialog("Error")
          }
        }
      }
    }
  }
}