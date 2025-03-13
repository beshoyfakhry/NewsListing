package com.beshoy.abroad.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beshoy.abroad.data.domain.NewsObject
import com.beshoy.abroad.data.domain.NewsResponse
import com.beshoy.abroad.data.repo.NewsRepository
import com.beshoy.abroad.data.repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepo: NewsRepository) : ViewModel() {


    private val _newsState = MutableStateFlow<Resource<NewsResponse>>(Resource.Loading)
    val newsState: StateFlow<Resource<NewsResponse>> = _newsState


    fun onSearchTextChanged(text: String) {
        if (text.length >= 3) {
            getNews(text)
        }
    }

    fun getNews(query: String) {
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                _newsState.value = newsRepo.getEverything(query)
            }
        }
    }
}