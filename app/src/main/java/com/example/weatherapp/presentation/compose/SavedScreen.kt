package com.example.weatherapp.presentation.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.presentation.MainViewModel

@Composable
fun SavedScreen() {
    
    val viewModel: MainViewModel = hiltViewModel()
    val lazyWeatherItems = viewModel.whetherItemsFlow.collectAsState(initial = emptyList())
    
    LazyColumn {
        items(lazyWeatherItems.value) { weatherItem ->
            Text(text = weatherItem.cityName)
        
        }
    }
}