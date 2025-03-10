package com.beshoy.abroad.data.repo

import com.beshoy.abroad.data.domain.NewsResponse
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsApi) {


    suspend fun getEverything(searchQuery: String? = null): NewsResponse {
        val query = searchQuery ?: "us"
        return api.getEverything(query)
    }

}