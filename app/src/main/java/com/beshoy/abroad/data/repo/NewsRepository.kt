package com.beshoy.abroad.data.repo

import com.beshoy.abroad.BuildConfig
import com.beshoy.abroad.data.domain.NewsResponse
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsAPI) {


    suspend fun getTopHeadlines(
        country: String = "us",
        apiKey: String = BuildConfig.API_KEY
    ): NewsResponse {
        return api.getTopHeadlines()
    }

}