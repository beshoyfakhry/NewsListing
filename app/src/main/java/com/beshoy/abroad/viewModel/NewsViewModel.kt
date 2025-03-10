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

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    private val _newsList = MutableStateFlow<Resource<NewsResponse>>(Resource.Loading)
    val newsList: StateFlow<Resource<NewsResponse>> = _newsList

    var searchText = mutableStateOf("")
        private set

    fun onSearchTextChanged(text: String) {
        searchText.value = text
        if (text.length >= 3) {
            getNews(text)
        } else if (text.isEmpty()) {
            getNews()
        }
    }

    init {
//        getNews()
    }

    fun getNews(query: String? = null) {
        viewModelScope.launch {

            _newsList.value = newsRepo.getEverything(query)
        }

    }


}