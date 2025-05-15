package com.beshoy.abroad.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.beshoy.abroad.data.domain.NewsObject

@Composable
fun NewsDetailsScreen(newsItem: NewsObject) {

    Column(modifier = Modifier.padding(8.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(newsItem.urlToImage)
                .crossfade(true)
//                .placeholder(coil.base.R.drawable.ic_100tb)
//                .error(androidx.loader.R.drawable.notification_bg_low)
                .scale(Scale.FILL)
                .build(),
            contentDescription = "Image from URL",
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {

                newsItem.author?.let {
                    Text(
                        text = it,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                newsItem.publishedAt?.let {
                    Text(
                        text = it,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                newsItem.content?.let {
                    Text(
                        text = it
                    )
                }
            }
        }
    }
}

