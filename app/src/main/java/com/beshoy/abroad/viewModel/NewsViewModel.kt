package com.beshoy.abroad.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beshoy.abroad.data.domain.NewsObject
import com.beshoy.abroad.data.repo.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepo: NewsRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading

    private val _newsList = MutableStateFlow<List<NewsObject>>(emptyList())
    val newsList: StateFlow<List<NewsObject>> = _newsList


    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {

            _newsList.value = newsRepo.getTopHeadlines().articles
            _isLoading.value = false
        }

    }


}