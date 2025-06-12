package com.beshoy.abroad.ui.components


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.beshoy.abroad.R


@Composable
fun CustomSearchAlertDialog(alertText: String) {
    val shouldShowDialog = remember { mutableStateOf(true) }

    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = stringResource(R.string.warning), tint = Color.Red)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.error), fontWeight = FontWeight.Bold)
                }
            },
            text = { Text(text = alertText) },
            confirmButton = {
                OutlinedButton(onClick = {

                    shouldShowDialog.value = false
                }) {
                    Text(stringResource(R.string.ok))
                }
            },
        )

    }
}