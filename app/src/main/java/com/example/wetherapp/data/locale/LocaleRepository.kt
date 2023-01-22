package com.example.wetherapp.data.locale

import com.example.wetherapp.data.locale.db.WeatherDao
import com.example.wetherapp.data.locale.db.WeatherItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocaleRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val dataStoreManager: DataStorageManager
) {
    suspend fun saveWeatherItem(weatherItem: WeatherItem) {
        weatherDao.insertWeatherItem(weatherItem)
    }
    
    suspend fun deleteWeatherItem(weatherItem: WeatherItem) {
        weatherDao.deleteWeatherItem(weatherItem)
    }
    
    fun observeWeatherItems(): Flow<List<WeatherItem>> {
        return weatherDao.observeWeatherItems()
    }
    
    suspend fun clearWeatherItemsDb() {
        weatherDao.clearDb()
    }
    
    suspend fun saveItemToPrefs(item:String) {
        dataStoreManager.saveItemToPrefs(item)
    }
}