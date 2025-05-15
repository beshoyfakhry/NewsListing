package com.beshoy.abroad.ui.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.beshoy.abroad.data.domain.NewsObject


@Composable
fun NewsItem(item: NewsObject, onItemClicked: (NewsObject) -> Unit = {}) {


    Card() {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    onItemClicked(item)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.urlToImage) // URL of the image
                    .crossfade(true)
//                    .placeholder(coil.base.R.drawable.ic_100tb) // Show placeholder while loading
//                    .error(androidx.loader.R.drawable.notification_bg_low) // Show error image if loading fails
                    .scale(Scale.FIT) // Control the scaling of the image
                    .build(),
                contentDescription = "Image from URL",
                modifier = Modifier.fillMaxWidth()
            )
            Column {
                item.title?.let { Text(text = it) }
                item.content?.let { Text(text = it) }
            }

        }
    }
}

@Preview
@Composable
fun NewsItemPreview() {

    val newsItem =
        NewsObject(
            "news author",
            "news title",
            "New desc",
            "https://media.geeksforgeeks.org/wp-content/cdn-uploads/gfg_200x200-min.png",
            "20/25/1995",
            "News details"
        )
    NewsItem(item = newsItem)
}