package com.beshoy.abroad.data.repo

import android.util.Log
import com.beshoy.abroad.data.domain.NewsResponse
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsApi) {

    suspend fun getEverything(searchQuery: String): Resource<NewsResponse> {
        return try {
            Resource.Success(api.getEverything(searchQuery))
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

}