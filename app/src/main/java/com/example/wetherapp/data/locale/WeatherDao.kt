package com.example.wetherapp.data.locale

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherItem(weatherItem: WeatherItem)
    
    @Delete
    suspend fun deleteWeatherItem(weatherItem: WeatherItem)
    
    @Query(value = "SELECT * FROM weather_items")
    fun observeWeatherItem(): Flow<WeatherItem>
}