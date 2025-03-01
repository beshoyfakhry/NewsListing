package com.beshoy.abroad.data.repo

import com.beshoy.abroad.BuildConfig
import com.beshoy.abroad.data.domain.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {



    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsResponse

}