package com.example.weatherapp.presentation.compose

import android.net.Uri
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
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weatherapp.R
import com.example.weatherapp.presentation.MainViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val weather = viewModel.detailsFlow.collectAsState().value
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        GlideImage(model = Uri.parse(weather.conditionIcon), contentDescription = "condition icon")
        Text(text = weather.cityName, style = MaterialTheme.typography.titleLarge)
        Text(text = weather.countryName, style = MaterialTheme.typography.titleMedium)
        Text(text = weather.temp.toString() + "ºC", style = MaterialTheme.typography.displayLarge)
        Text(text = weather.condition, style = MaterialTheme.typography.titleLarge)
        Row {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_wind),
                contentDescription = "wind"
            )
            Text(text = "wind" + weather.windKmh, style = MaterialTheme.typography.titleMedium)
        }
    }
    
}