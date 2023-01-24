package com.example.weatherapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.MainRepository
import com.example.weatherapp.data.locale.db.WeatherItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    
    private val ioScope = CoroutineScope(viewModelScope.coroutineContext + Dispatchers.IO)
    
    private val _detailsFlow = MutableStateFlow<WeatherItem?>(null)
    val detailsFlow = _detailsFlow.asStateFlow()
    
    val whetherItemsFlow = mainRepository.observeWeatherItems()
    val historyItemsFlow = mainRepository.observeRequestHistory().map {
        it.toMutablePreferences()
            .asMap()
            .mapKeys { (k, _) -> k.name }
            .mapValues { (_, v) -> v.toString().toLongOrNull() }
            .toList()
            .sortedBy { (_, v) -> v }
            .reversed()
            .map { (k, _) -> k }
    }
    
//    fun searchWeather(request: String) {
//        ioScope.launch {
//            mainRepository.saveRequestHistoryItem(request)
//            val replyFromDb = mainRepository.findWeatherItemByCityName(request)
//            if (replyFromDb != null) {
//                return@launch emitReply(replyFromDb)
//                Log.d("AAA", "Loaded from database")
//
//            } else {
//                val result = mainRepository.getWeatherFromRemote(request)!!.toWeatherItem()
//                _detailsFlow.value = result
//                Log.d("AAA", "Loaded from net")
//                Log.d("AAA", result.toString())
//                mainRepository.saveWeatherItem(result)
//            }
//        }
//    }
    
    fun searchWeather2(request: String): WeatherItem? {
        var result: WeatherItem? = WeatherItem()
        ioScope.launch {
            val deferredResult = async { mainRepository.findWeatherItemByCityName(request) }
            result = deferredResult.await()
        }
        return result
    }
    
    fun emitReply(reply: WeatherItem): Flow<WeatherItem?> {
        return flow {
            emit(reply)
        }.flowOn(Dispatchers.IO)
    }
    
    //    fun findWeatherItemById(weatherItemId: String): Flow<WeatherItem> {
//        return flow {
//            val result = mainRepository.findWeatherItemById(weatherItemId)
//            emit(result)
//        }.flowOn(Dispatchers.IO)
//    }
    fun getWeather(cityName: String) {
        ioScope.launch {
//            val result = mainRepository.getWeatherFromRemote(cityName)
                val result = mainRepository.findWeatherItemByCityName(cityName)
                _detailsFlow.value = result
            }
    }
    
    fun getWeatherItemById(weatherItemId: String): WeatherItem? {
        var result: WeatherItem? = WeatherItem()
        ioScope.launch {
            val deferredResult = async { mainRepository.findWeatherItemById(weatherItemId) }
            result = deferredResult.await()
        }
        return result
    }
    
    fun getWeatherItemByCityName(cityName: String): WeatherItem? {
        var result: WeatherItem? = WeatherItem()
        ioScope.launch {
            val deferredResult = async { mainRepository.findWeatherItemByCityName(cityName) }
            result = deferredResult.await()
        }
        return result
    }
    
//    fun getWeatherFromRemote(cityName: String): WeatherItem? {
//        var result: WeatherItem? = WeatherItem()
//        ioScope.launch {
//            val deferredResult = async { mainRepository.getWeatherFromRemote(cityName)?.toWeatherItem() }
//            result = deferredResult.await()
//        }
//        return result
//    }
    
    fun deleteRequest(item: String) {
        ioScope.launch {
            mainRepository.deleteRequestHistoryItem(item)
        }
    }
    
    fun clearRequestHistory() {
        ioScope.launch {
            mainRepository.clearRequestHistory()
        }
    }
    
    fun deleteWeatherItem(item: WeatherItem) {
        ioScope.launch {
            mainRepository.deleteWeatherItem(item)
        }
    }
}