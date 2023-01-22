package com.example.wetherapp.data.locale

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_items")
data class WeatherItem(
    @PrimaryKey
    val id: String,
    val cityName: String,
    val country: String,
    val lastUpdated: Int,
    val temp: Double,
    val condition: String,
    val conditionIcon: String,
    val windKmh: Double
)
