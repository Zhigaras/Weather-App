package com.example.weatherapp.data.locale.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherItem(weatherItem: WeatherItem)
    
    @Query("SELECT * FROM weather_items WHERE cityName LIKE :request")
    suspend fun findWeatherItem(request: String): WeatherItem?
    
    @Delete()
    suspend fun deleteWeatherItem(weatherItem: WeatherItem)
    
    @Query("SELECT * FROM weather_items ORDER BY lastUpdatedEpoch DESC")
    fun observeWeatherItems(): Flow<List<WeatherItem>>
    
    @Query("DELETE FROM weather_items")
    suspend fun clearDb()
}