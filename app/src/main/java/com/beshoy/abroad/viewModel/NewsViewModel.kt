package com.beshoy.abroad.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beshoy.abroad.data.repo.NewsRepository
import com.beshoy.abroad.data.repo.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
  private val newsRepo: NewsRepository
) : ViewModel() {


  private val _newsState = MutableStateFlow<ResourceState>(ResourceState.Loading)
  val newsState: StateFlow<ResourceState> = _newsState

  private val _queryState = MutableStateFlow("")
  val queryState: StateFlow<String> = _queryState

  private var searchJob: Job? = null

  fun onSearchTextChanged(text: String) {
    _queryState.update { text }
    searchJob?.cancel()
    if (queryState.value.length >= 3) {
      getNews(queryState.value)
    }
  }

  fun getNews(query: String) {
    if (query.isNotEmpty()) {
      searchJob = viewModelScope.launch {
        delay(700)
        _newsState.value = ResourceState.Loading
        val result = withContext(Dispatchers.IO) {
          newsRepo.getEverything(query)
        }
        _newsState.value = result
      }
    }
  }
}