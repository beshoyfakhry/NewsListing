package com.beshoy.abroad.data.repo

import com.beshoy.abroad.data.domain.NewsResponse
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsApi) {


    suspend fun getEverything(searchQuery: String? = null): Resource<NewsResponse> {

        try {
            val query = searchQuery ?: "us"
            return Resource.Success(api.getEverything(query))
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
    }


}