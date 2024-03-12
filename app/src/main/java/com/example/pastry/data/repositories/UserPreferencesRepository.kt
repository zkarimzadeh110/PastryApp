package com.example.pastry.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.pastry.data.repositories.UserPreferencesRepository.PreferencesKeys.TOKEN
import com.example.pastry.ui.contents.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class  UserPreferencesRepository @Inject constructor(@ApplicationContext applicationContext: Context):PreferenceRepository {
    
    var dataStore=applicationContext.dataStore
    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("token")
    }

    override suspend fun getToken():String{
        val preferences = dataStore.data.first()
        return preferences[TOKEN]?: ""
    }
    override suspend fun saveTokenToPreferencesStore(token:String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] =token
        }
    }

}