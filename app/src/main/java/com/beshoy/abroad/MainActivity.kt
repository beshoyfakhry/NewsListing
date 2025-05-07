package com.beshoy.abroad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.beshoy.abroad.data.domain.NewsObject
import com.beshoy.abroad.ui.screens.NewsDetailsScreen
import com.beshoy.abroad.ui.screens.NewsListingScreen
import com.beshoy.abroad.ui.screens.Screen
import com.beshoy.abroad.ui.theme.AbroadTheme
import com.beshoy.abroad.viewModel.NetworkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //   installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AbroadTheme {
                val networkViewModel: NetworkViewModel = hiltViewModel()
                val isConnected by networkViewModel.isConnected.collectAsStateWithLifecycle(false)
                networkViewModel.registerNetworkCallback()
                AppScaffold(isConnected)
            }
        }
    }
}


@Composable
fun AppScaffold(isConnected: Boolean) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopAppBarWithConnectionStatus(navController, isConnected = isConnected) }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)

        ) {
            MainNavigation(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithConnectionStatus(navController: NavHostController, isConnected: Boolean) {
    TopAppBar(
        title = {
//            Text(
//                text = if (isConnected) "Connected ✅" else "No Internet ❌"
//            )
        }, actions = {
            IconButton(onClick = {
                navController.navigate("SearchNewsListing")
            }) {
                Icon(
                    imageVector = Icons.Default.Search, // Replace with the icon you want
                    contentDescription = "Refresh"
                )
            }
        }
    )
}


@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.NewsListing.route) {

        composable(Screen.NewsListing.route) { NewsListingScreen(navController, isSearch = false) }
        composable(Screen.NewsDetails.route) {
            val news =
                navController.previousBackStackEntry?.savedStateHandle?.get<NewsObject>("news")
            news?.let { NewsDetailsScreen(it) }

        }
//        composable("SplashScreen") { SplashScreen(navController) }
        composable(Screen.SearchNews.route) { NewsListingScreen(navController, true) }
    }
}