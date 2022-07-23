package id.stefanusdany.storyapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import id.stefanusdany.storyapp.data.local.StoryDatabase
import id.stefanusdany.storyapp.data.remote.retrofit.ApiConfig
import id.stefanusdany.storyapp.model.UserPreference
import id.stefanusdany.storyapp.repository.Repository

object Injection {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    fun provideRepository(context: Context): Repository {
        val storyDatabase = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig().getInstance()
        val userPreference = UserPreference.getInstance(context.dataStore)
        return Repository.getInstance(storyDatabase, apiService, userPreference)
    }
}