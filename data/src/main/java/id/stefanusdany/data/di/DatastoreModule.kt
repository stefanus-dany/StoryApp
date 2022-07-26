package id.stefanusdany.data.di

import javax.inject.Singleton
import android.content.Context
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import id.stefanusdany.data.datastore.IUserPreference
import id.stefanusdany.data.datastore.UserPreference

@Module(includes = [Datastore::class])
abstract class DatastoreModule {

    @Binds
    abstract fun provideDatastore(userPreference: UserPreference): IUserPreference
}

@Module
class Datastore {

    @Singleton
    @Provides
    fun provideUserPreference(context: Context) =
        RxPreferenceDataStoreBuilder(context, "settings").build()
}