package com.example.pastry.di

import com.example.pastry.data.network.ApiService
import com.example.pastry.data.repositories.PreferenceRepository
import com.example.pastry.data.repositories.UserPreferencesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object MainModule {
    @Singleton
    @Provides
    fun getApiService(moshi: Moshi,okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("http://111.111.1.11/pastry/")
             .client(okHttpClient)
            .build()
            .create()
    }
    @Singleton
    @Provides
    fun getOkhttpClient(userPreferencesRepository: UserPreferencesRepository): OkHttpClient =  OkHttpClient.Builder()
            .addInterceptor {chain->
                val token= runBlocking { userPreferencesRepository.getToken() }
                val newRequest=chain.request().newBuilder()
                    .header("Authorization",token)
                    .build()
               chain.proceed(newRequest)
            }.build()

    @Singleton
    @Provides
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {

    @Binds
    abstract fun bindPreferenceRepository(
        analyticsServiceImpl: UserPreferencesRepository
    ): PreferenceRepository
}