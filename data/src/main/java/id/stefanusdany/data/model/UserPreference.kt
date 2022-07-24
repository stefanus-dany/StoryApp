package id.stefanusdany.data.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import id.stefanusdany.data.data.remote.response.LoginResultResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUserInfo(): Flow<LoginResultResponse> {
        return dataStore.data.map { preferences ->
            LoginResultResponse(
                preferences[USER_ID] ?: "",
                preferences[USER_NAME] ?: "",
                preferences[TOKEN] ?: ""
            )
        }
    }

    suspend fun login(userId: String, userName: String, token: String) {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = true
            preferences[USER_ID] = userId
            preferences[USER_NAME] = userName
            preferences[TOKEN] = token
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = false
            preferences[USER_ID] = ""
            preferences[USER_NAME] = ""
            preferences[TOKEN] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val STATE_KEY = booleanPreferencesKey("state")
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val TOKEN = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}