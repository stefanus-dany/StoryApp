package id.stefanusdany.data.datastore

import javax.inject.Inject
import javax.inject.Singleton
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.rxjava2.RxDataStore
import id.stefanusdany.data.data.remote.response.LoginResultResponse
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class UserPreference @Inject constructor(private val dataStore: RxDataStore<Preferences>) :
    IUserPreference {

    override fun getUserInfo(): Flowable<LoginResultResponse> {
        return dataStore.data().map { preferences ->
            LoginResultResponse(
                preferences[USER_ID] ?: "",
                preferences[USER_NAME] ?: "",
                preferences[TOKEN] ?: ""
            )
        }
    }

    override fun login(userId: String, userName: String, token: String) {
        dataStore.updateDataAsync { prefsIn: Preferences ->
            val mutablePreferences = prefsIn.toMutablePreferences()
            mutablePreferences[STATE_KEY] = true
            mutablePreferences[USER_ID] = userId
            mutablePreferences[USER_NAME] = userName
            mutablePreferences[TOKEN] = token
            Single.just(
                mutablePreferences
            )
        }
    }

    override fun logout() {
        dataStore.updateDataAsync { prefsIn: Preferences ->
            val mutablePreferences = prefsIn.toMutablePreferences()
            mutablePreferences[STATE_KEY] = false
            mutablePreferences[USER_ID] = ""
            mutablePreferences[USER_NAME] = ""
            mutablePreferences[TOKEN] = ""
            Single.just(
                mutablePreferences
            )
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val STATE_KEY = booleanPreferencesKey("state")
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val TOKEN = stringPreferencesKey("token")

//        fun getInstance(dataStore: RxDataStore<Preferences>): UserPreference {
//            return INSTANCE ?: synchronized(this) {
//                val instance = UserPreference(dataStore)
//                INSTANCE = instance
//                instance
//            }
//        }
    }
}