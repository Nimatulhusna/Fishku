package com.capstone.fishku.data.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FishPreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val fishUser = stringPreferencesKey("key_fish")

    fun getFish(): Flow<String?>{
        return dataStore.data.map { preferences ->
            preferences[fishUser] ?: ""
        }
    }

    suspend fun saveFish(key: String) {
        dataStore.edit { preferences ->
            preferences[fishUser] = key
        }
    }

    suspend fun deleteFish() {
        dataStore.edit { preferences ->
            preferences.remove(fishUser)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: FishPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): FishPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = FishPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}