package com.beshoy.abroad.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.beshoy.abroad.R
import com.beshoy.abroad.data.domain.NewsObject


@Composable
fun NewsItem(item: NewsObject, onItemClicked: (NewsObject) -> Unit = {}) {


    Card() {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    onItemClicked(item)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_image_24),
                contentDescription = "News main image",
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
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