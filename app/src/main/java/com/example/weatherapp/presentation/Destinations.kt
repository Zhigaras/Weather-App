package com.example.weatherapp.presentation

import com.example.weatherapp.R

interface Destination {
    val icon: Int
    val route: String
}

object Search:Destination {
    override val icon = R.drawable.baseline_search_24
    override val route = "Search"
}

object Saved : Destination {
    override val icon = R.drawable.baseline_location_city_24
    override val route = "Saved"
}

object DetailsScreen : Destination {
    override val icon = R.drawable.baseline_wb_sunny_24
    override val route = "Details"
}

val bottomTabList = listOf(Search, Saved)