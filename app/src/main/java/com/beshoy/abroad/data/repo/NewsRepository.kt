package com.beshoy.abroad.data.repo

import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsApi) {

    suspend fun getEverything(searchQuery: String): ResourceState{
        return try {
            ResourceState.Success(api.getEverything(searchQuery))
        } catch (e: Exception) {
            ResourceState.Error(e.message.toString())
        }
    }

}