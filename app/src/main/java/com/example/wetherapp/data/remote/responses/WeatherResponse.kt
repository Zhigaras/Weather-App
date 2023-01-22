package com.example.wetherapp.data.remote.responses


import com.example.wetherapp.data.locale.db.WeatherItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "current")
    val current: Current,
    @Json(name = "location")
    val location: Location
) {
    
    fun toWeatherItem(): WeatherItem {
        return WeatherItem(
            id = location.name + current.lastUpdated.takeWhile { it != ' ' },
            cityName = location.name,
            country = location.country,
            lastUpdated = current.lastUpdatedEpoch,
            temp = current.tempC,
            condition = current.condition.text,
            conditionIcon = current.condition.icon,
            windKmh = current.windKph
        )
    }
}