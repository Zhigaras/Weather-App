package com.example.weatherapp.presentation.savedscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.MainRepository
import com.example.weatherapp.data.locale.db.WeatherItem
import com.example.weatherapp.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    
    private val ioScope = CoroutineScope(viewModelScope.coroutineContext + ioDispatcher)
    
    val whetherItemsFlow = mainRepository.observeWeatherItems()
    
    fun deleteWeatherItem(item: WeatherItem) {
        ioScope.launch {
            mainRepository.deleteWeatherItem(item)
        }
    }
}