package com.beshoy.abroad.data

import com.beshoy.abroad.data.repo.NewsAPI
import com.beshoy.abroad.utils.NetworkConnectionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    val apiBaseUrl = "https://newsapi.org/"

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        // Create a logging interceptor to log the HTTP request/response
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY // Set the level of logging

        // Create and return OkHttpClient with logging interceptor
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsAPI(retrofit: Retrofit): NewsAPI {
        return retrofit.create(NewsAPI::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideNetworkConnectionService(networkConnectionService: NetworkConnectionService): NetworkConnectionService {
//        return networkConnectionService
//    }


}