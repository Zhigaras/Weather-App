package com.example.weatherapp.presentation.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.weatherapp.R
import com.example.weatherapp.presentation.MainViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreen(cityName: String) {
    val viewModel: MainViewModel = hiltViewModel()
    viewModel.getWeather(cityName)
    val weather = viewModel.detailsFlow.collectAsState().value
    Log.d("AAA", cityName)
    Log.d("AAA", weather.toString())
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        weather?.let {
    
//            GlideImage(model = Uri.parse(it.conditionIcon), contentDescription = "condition icon")
            Text(text = it.cityName, style = MaterialTheme.typography.titleLarge)
            Text(text = it.countryName, style = MaterialTheme.typography.titleMedium)
            Text(text = it.temp.toString() + "ºC", style = MaterialTheme.typography.displayLarge)
            Text(text = it.condition, style = MaterialTheme.typography.titleLarge)
            Row {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_wind),
                    contentDescription = "wind"
                )
                Text(text = "wind" + it.windKmh, style = MaterialTheme.typography.titleMedium)
            }
            
        }
    }
}