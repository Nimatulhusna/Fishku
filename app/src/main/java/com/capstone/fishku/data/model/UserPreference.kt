package com.capstone.fishku.data.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val keyUser = stringPreferencesKey("key_user")

    fun getUser(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[keyUser] ?: ""
        }
    }

    suspend fun saveUser(key: String) {
        dataStore.edit { preferences ->
            preferences[keyUser] = key
        }
    }

    suspend fun deleteUser() {
        dataStore.edit { preferences ->
            preferences.remove(keyUser)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}