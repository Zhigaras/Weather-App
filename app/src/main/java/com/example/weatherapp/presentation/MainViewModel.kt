package com.example.weatherapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val whetherItemsFlow = mainRepository.observeWeatherItems()
    val historyItemsFlow = mainRepository.observeRequestHistory().map { it.toMutablePreferences()
        .asMap()
        .mapKeys { (k, _) -> k.name }
        .mapValues { (_, v) -> v.toString().toLongOrNull() }
        .toList()
        .sortedBy { (_,v) -> v }
        .reversed()
        .map { (k,_) -> k } }
    
    fun searchWeather(city: String) {
        viewModelScope.launch {
            val result = mainRepository.getWeather(city)!!.toWeatherItem()
            Log.d("AAA", result.toString())
            mainRepository.saveWeatherItem(result)
        }
    }
    
    fun saveRequest(item: String) {
        viewModelScope.launch {
            mainRepository.saveRequestItem(item)
        }
    }
}