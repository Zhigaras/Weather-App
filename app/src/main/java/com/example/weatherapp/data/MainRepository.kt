package com.example.weatherapp.data

import androidx.datastore.preferences.core.Preferences
import com.example.weatherapp.data.locale.LocaleRepository
import com.example.weatherapp.data.locale.db.WeatherItem
import com.example.weatherapp.data.remote.RemoteRepository
import com.example.weatherapp.data.remote.responses.WeatherResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localeRepository: LocaleRepository
) {

    suspend fun getWeatherFromRemote(city: String): Response<WeatherResponse> {
        return remoteRepository.getWeather(city)
    }
    
    suspend fun saveWeatherItem(weatherItem: WeatherItem) {
        localeRepository.saveWeatherItem(weatherItem)
    }
    
    suspend fun findWeatherItemByCityName(request: String): WeatherItem? {
        return localeRepository.findWeatherItemByCityName(request)
    }
    
    suspend fun findWeatherItemById(request: String): WeatherItem {
        return localeRepository.findWeatherItemById(request)
    }
    
    suspend fun deleteWeatherItem(weatherItem: WeatherItem) {
        localeRepository.deleteWeatherItem(weatherItem)
    }

    fun observeWeatherItems(): Flow<List<WeatherItem>> {
        return localeRepository.observeWeatherItems()
    }
    
    suspend fun clearWeatherItemsDb() {
        localeRepository.clearWeatherItemsDb()
    }
    
    suspend fun saveRequestHistoryItem(item:String) {
        localeRepository.saveItemToPrefs(item)
    }
    
    suspend fun deleteRequestHistoryItem(item: String) {
        localeRepository.deleteItemFromPrefs(item)
    }
    
    suspend fun clearRequestHistory() {
        localeRepository.clearPrefs()
    }
    
    fun observeRequestHistory(): Flow<Preferences> {
        return localeRepository.observePrefs()
    }
}