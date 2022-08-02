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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Module
@InstallIn(SingletonComponent::class)
class StoryDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): StoryDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("stefanusdany".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context.applicationContext,
            StoryDatabase::class.java,
            "story_database.db"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideListStoryDao(database: StoryDatabase): ListStoryDao = database.listStoryDao()
}