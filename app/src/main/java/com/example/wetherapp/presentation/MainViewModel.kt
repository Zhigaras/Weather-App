package com.example.wetherapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wetherapp.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    fun test() {
        viewModelScope.launch {
            val result = mainRepository.getWeather("moscow")
            Log.d("AAA", result?.toWeatherItem().toString())
        }
    }
}