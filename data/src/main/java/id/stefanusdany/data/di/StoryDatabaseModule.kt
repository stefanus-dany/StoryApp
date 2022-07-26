package id.stefanusdany.data.di

import javax.inject.Singleton
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.stefanusdany.data.data.local.ListStoryDao
import id.stefanusdany.data.data.local.StoryDatabase

@Module
@InstallIn(SingletonComponent::class)
class StoryDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): StoryDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            StoryDatabase::class.java,
            "story_database.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideListStoryDao(database: StoryDatabase): ListStoryDao = database.listStoryDao()
}