package com.beshoy.abroad.ui.screens

sealed class Screen(val route: String) {

  companion object {
    fun fromRoute(route: String?): Screen {
      return when(route) {
        NewsListing.route -> NewsListing
        NewsDetails.route -> NewsDetails
        NewsSearchScreen.route -> NewsSearchScreen
        else -> NewsListing
      }
    }
  }

  object NewsListing : Screen("NewsListing")
  object NewsDetails : Screen("newsDetail")
  object NewsSearchScreen : Screen("SearchNewsListing")

}