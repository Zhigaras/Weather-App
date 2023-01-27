package com.example.weatherapp.presentation.searchscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.MainRepository
import com.example.weatherapp.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    
    private val ioScope = CoroutineScope(viewModelScope.coroutineContext + ioDispatcher)
    
    val historyItemsFlow = mainRepository.observeRequestHistory()
    
    
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
}