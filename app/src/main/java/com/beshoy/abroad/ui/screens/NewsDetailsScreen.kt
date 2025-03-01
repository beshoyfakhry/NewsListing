package com.beshoy.abroad.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.beshoy.abroad.R
import com.beshoy.abroad.data.domain.NewsObject


@Composable
fun NewsDetailsScreen(newsItem: NewsObject) {

    Column(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.baseline_image_24),
            contentDescription = "News main image",
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(1f)
        )
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = newsItem.author,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = newsItem.publishedAt,
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

@Preview
@Composable
fun NewsDetailsScreenPreview() {
    val newsObj = NewsObject(
        "Author ", "title ", "desc ", "", "Publish at ", "News content "
    )
    NewsDetailsScreen(newsItem = newsObj)
}