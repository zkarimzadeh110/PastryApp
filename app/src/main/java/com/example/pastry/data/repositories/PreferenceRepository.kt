package com.example.pastry.data.repositories

interface  PreferenceRepository {
    suspend fun getToken():String
    suspend fun saveTokenToPreferencesStore(token:String)
}