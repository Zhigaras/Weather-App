package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.responses.WeatherResponse
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val weatherApi: WeatherApi
) {
    
    suspend fun getWeather(city:String): WeatherResponse? {
        val result = weatherApi.getWeather(city)
        if (result.isSuccessful) {
            return result.body()
        }
        return null
    }
    
//    suspend fun getWeather(city: String) {
//        val weatherLivaData = flow {
//            emit(ApiResult.Loading(isLoading = true))
//            val response = weatherApi.getWeather(city)
//            if (response.isSuccessful) {
//                emit(ApiResult.Success(response.body()))
//            } else {
//                val errorMsg = response.errorBody()?.string()
//                response.errorBody()?.close()  // remember to close it after getting the stream of error body
//                emit(ApiResult.Error(errorMsg.toString()))
//            }
//        }.map {
//            it.data?.toWeatherItem()
//        }.asLiveData()
//    }

}