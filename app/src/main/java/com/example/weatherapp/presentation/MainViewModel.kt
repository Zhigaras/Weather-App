package com.example.weatherapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.MainRepository
import com.example.weatherapp.data.locale.db.WeatherItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    
    private val ioScope = CoroutineScope(viewModelScope.coroutineContext + Dispatchers.IO)
    
    private val _detailsFlow = MutableStateFlow(WeatherItem())
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
    
    fun searchWeather(request: String) {
        ioScope.launch {
            mainRepository.saveRequestHistoryItem(request)
            val replyFromDb = mainRepository.findWeatherItem(request)
            if (replyFromDb != null) {
                _detailsFlow.value = replyFromDb
                Log.d("AAA", "Loaded from database")
    
            } else {
                val result = mainRepository.getWeather(request)!!.toWeatherItem()
                _detailsFlow.value = result
                Log.d("AAA", "Loaded from net")
                Log.d("AAA", result.toString())
                mainRepository.saveWeatherItem(result)
            }
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