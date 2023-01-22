package com.example.wetherapp.data

import com.example.wetherapp.data.locale.LocaleRepository
import com.example.wetherapp.data.remote.RemoteRepository
import com.example.wetherapp.data.remote.responses.WeatherResponse
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localeRepository: LocaleRepository
) {

    suspend fun getWeather(city: String): WeatherResponse? {
        return remoteRepository.getWeather(city)
    }

}