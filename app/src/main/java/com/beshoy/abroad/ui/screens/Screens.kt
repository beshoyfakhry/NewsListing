package com.beshoy.abroad.ui.screens

sealed class Screen(val route: String) {

  companion object {
    fun fromRoute(route: String?): Screen {
      return when(route) {
        NewsListing.route -> NewsListing
        NewsDetails.route -> NewsDetails
        SearchNews.route -> SearchNews
        else -> NewsListing
      }
    }
  }

  object NewsListing : Screen("NewsListing")
  object NewsDetails : Screen("newsDetail")
  object SearchNews : Screen("SearchNewsListing")

}