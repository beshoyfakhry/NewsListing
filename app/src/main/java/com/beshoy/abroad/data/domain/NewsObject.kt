package com.beshoy.abroad.data.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class NewsObject(
    var author: String?,
    var title: String?,
    var description: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?
) : Parcelable


