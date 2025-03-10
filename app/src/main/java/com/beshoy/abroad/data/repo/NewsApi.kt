package com.beshoy.abroad.data.repo

import com.beshoy.abroad.BuildConfig
import com.beshoy.abroad.data.domain.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {


    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") query: String?,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsResponse

}