package com.beshoy.abroad.data.domain

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsObject>
)