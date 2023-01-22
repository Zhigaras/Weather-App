package com.example.weatherapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val dbFlow = mainRepository.observeWeatherItems()
    
    fun test() {
        viewModelScope.launch {
            val result = mainRepository.getWeather("moscow")!!.toWeatherItem()
            Log.d("AAA", result.toString())
            mainRepository.saveWeatherItem(result)
        }
    }
}