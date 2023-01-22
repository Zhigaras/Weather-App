package com.example.wetherapp.data

import com.example.wetherapp.data.locale.LocaleRepository
import com.example.wetherapp.data.locale.db.WeatherItem
import com.example.wetherapp.data.remote.RemoteRepository
import com.example.wetherapp.data.remote.responses.WeatherResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localeRepository: LocaleRepository
) {

    suspend fun getWeather(city: String): WeatherResponse? {
        return remoteRepository.getWeather(city)
    }
    
    suspend fun saveWeatherItem(weatherItem: WeatherItem) {
        localeRepository.saveWeatherItem(weatherItem)
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
}