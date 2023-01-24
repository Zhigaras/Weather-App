package com.example.weatherapp.data.locale

import androidx.datastore.preferences.core.Preferences
import com.example.weatherapp.data.locale.db.WeatherDao
import com.example.weatherapp.data.locale.db.WeatherItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocaleRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val dataStoreManager: DataStorageManager
) {
    suspend fun saveWeatherItem(weatherItem: WeatherItem) {
        weatherDao.insertWeatherItem(weatherItem)
    }
    
    suspend fun findWeatherItemByCityName(request: String): WeatherItem? {
        return weatherDao.findWeatherItemByCityName(request)
    }
    
    suspend fun findWeatherItemById(weatherItemId: String): WeatherItem {
        return weatherDao.findWeatherItemById(weatherItemId)
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
    
    suspend fun deleteItemFromPrefs(item: String) {
        dataStoreManager.deleteItemFromPrefs(item)
    }
    
    suspend fun clearPrefs() {
        dataStoreManager.clearPrefs()
    }
    
    fun observePrefs(): Flow<Preferences> {
        return dataStoreManager.observePrefs()
    }
}