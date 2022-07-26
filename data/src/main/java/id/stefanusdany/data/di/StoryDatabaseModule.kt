package id.stefanusdany.data.di

import javax.inject.Singleton
import android.content.Context
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder
import androidx.room.Room
import dagger.Module
import dagger.Provides
import id.stefanusdany.data.data.local.ListStoryDao
import id.stefanusdany.data.data.local.StoryDatabase

@Module
class StoryDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): StoryDatabase =
        Room.databaseBuilder(context.applicationContext, StoryDatabase::class.java, "story_database.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideListStoryDao(database: StoryDatabase): ListStoryDao= database.listStoryDao()

    @Singleton
    @Provides
    fun provideUserPreference(context: Context) = RxPreferenceDataStoreBuilder(context, "settings").build()
}