package com.beshoy.abroad.data.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class NewsObject(
    val author: String?,
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
) : Parcelable


