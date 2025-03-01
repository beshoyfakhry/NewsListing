package com.beshoy.abroad.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Splash Screen")
        LaunchedEffect(Unit) {

            delay(100)
            navController.navigate("NewsListing") {
                // Clear the back stack to avoid user navigating back to the NetworkStatusScreen
                popUpTo("SplashScreen") { inclusive = true }
            }
        }
    }
}