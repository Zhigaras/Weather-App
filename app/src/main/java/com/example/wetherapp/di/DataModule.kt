package com.example.wetherapp.di

import android.content.Context
import androidx.room.Room
import com.example.wetherapp.data.locale.WeatherDao
import com.example.wetherapp.data.locale.WeatherItemDatabase
import com.example.wetherapp.data.remote.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    
    @Provides
    @Singleton
    fun providesRetrofit(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(WeatherApi.BASE_URL)
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                }).build()
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
    
    @Provides
    @Singleton
    fun providesWeatherDatabase(
        @ApplicationContext app: Context
    ): WeatherItemDatabase {
        return Room.databaseBuilder(
            app,
            WeatherItemDatabase::class.java,
            WeatherItemDatabase.DATABASE_NAME
        ).build()
    }
    
    @Provides
    @Singleton
    fun providesWeatherDao(db: WeatherItemDatabase): WeatherDao {
        return db.getWeatherDao()
    }
}