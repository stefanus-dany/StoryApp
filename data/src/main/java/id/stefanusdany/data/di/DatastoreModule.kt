package id.stefanusdany.data.di

import javax.inject.Singleton
import android.content.Context
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.stefanusdany.data.datastore.IUserPreference
import id.stefanusdany.data.datastore.UserPreference

@Module
@InstallIn(SingletonComponent::class)
abstract class DatastoreModule {

    @Binds
    abstract fun provideDatastore(userPreference: UserPreference): IUserPreference
}

@Module
@InstallIn(SingletonComponent::class)
class Datastore {

    @Singleton
    @Provides
    fun provideUserPreference(@ApplicationContext context: Context) =
        RxPreferenceDataStoreBuilder(context, "settings").build()
}