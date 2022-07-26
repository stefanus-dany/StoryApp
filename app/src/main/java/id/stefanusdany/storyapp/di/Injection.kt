package id.stefanusdany.storyapp.di

import android.content.Context
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder
import id.stefanusdany.core.helper.utils.AppExecutors
import id.stefanusdany.data.data.local.LocalDataSource
import id.stefanusdany.data.data.local.StoryDatabase
import id.stefanusdany.data.data.remote.RemoteDataSource
import id.stefanusdany.data.data.remote.retrofit.ApiConfig
import id.stefanusdany.data.data.repository.Repository
import id.stefanusdany.data.model.UserPreference
import id.stefanusdany.domain.repository.IRepository
import id.stefanusdany.domain.usecase.auth.AuthInteractor
import id.stefanusdany.domain.usecase.auth.AuthUseCase
import id.stefanusdany.domain.usecase.story.StoryInteractor
import id.stefanusdany.domain.usecase.story.StoryUseCase


object Injection {

//    fun provideRepository(context: Context): IRepository {
//        val storyDatabase = StoryDatabase.getDatabase(context)
//        val dataStore = RxPreferenceDataStoreBuilder(context, "settings").build()
//        val userPreferences = UserPreference.getInstance(dataStore)
//        val localDataSource =
//            LocalDataSource.getInstance(storyDatabase.listStoryDao(), userPreferences)
//        val apiService = ApiConfig().getInstance()
//        val remoteDataSource = RemoteDataSource.getInstance(apiService)
//        val appExecutors = AppExecutors()
//        return Repository.getInstance(localDataSource, remoteDataSource, appExecutors)
//    }
//
//    fun provideAuthUseCase(context: Context): AuthUseCase =
//        AuthInteractor(provideRepository(context))
//
//    fun provideStoryUseCase(context: Context): StoryUseCase =
//        StoryInteractor(provideRepository(context))
}