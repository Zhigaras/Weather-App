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
    
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _errorFlow.value = throwable.localizedMessage
    }
    
    private val ioScope = CoroutineScope(
        viewModelScope.coroutineContext +
                Dispatchers.IO +
                coroutineExceptionHandler
    )
    
    private val _detailsFlow = MutableStateFlow<WeatherItem>(WeatherItem())
    val detailsFlow = _detailsFlow.asStateFlow()
    
    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow = _errorFlow.asStateFlow()
    
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
    
    fun getWeather(cityName: String) {
        ioScope.launch {
            val dbReply = mainRepository.findWeatherItemByCityName(cityName)
            if (dbReply != null) {
                _detailsFlow.value = dbReply
                Log.d("XXX", "loaded from db")
            } else {
                val remoteResponse = mainRepository.getWeatherFromRemote(cityName)
                if (remoteResponse.isSuccessful) {
                    val result = remoteResponse.body()?.toWeatherItem()
                    if (result != null) {
                        mainRepository.saveWeatherItem(result)
                        _detailsFlow.value = result
                        _errorFlow.value = null
                        Log.d("XXX", "loaded from remote")
                    } else {
                        _errorFlow.value = "Network error. Try again please"
                    }
                }
            }
        }
    }
    
    fun saveRequestHistoryItem(item: String) {
        ioScope.launch {
            mainRepository.saveRequestHistoryItem(item)
        }
    }
    
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