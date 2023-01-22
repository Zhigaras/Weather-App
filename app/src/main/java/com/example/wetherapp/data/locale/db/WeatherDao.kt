package com.example.wetherapp.data.locale.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherItem(weatherItem: WeatherItem)
    
    @Delete()
    suspend fun deleteWeatherItem(weatherItem: WeatherItem)
    
    @Query("SELECT * FROM weather_items ORDER BY lastUpdated DESC")
    fun observeWeatherItems(): Flow<List<WeatherItem>>
    
    @Query("DELETE FROM weather_items")
    suspend fun clearDb()
}