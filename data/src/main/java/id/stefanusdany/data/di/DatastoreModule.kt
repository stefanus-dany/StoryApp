//package id.stefanusdany.data.di
//
//import javax.inject.Singleton
//import android.content.Context
//import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import id.stefanusdany.data.datastore.IUserPreference
//import id.stefanusdany.data.datastore.UserPreference
//import id.stefanusdany.data.model.UserPreference
//
//@Module
//class DatastoreModule {
//
//    @Singleton
//    @Provides
//    fun provideDatastore(context: Context): IUserPreference  =
//        UserPreference(RxPreferenceDataStoreBuilder(context, "settings").build())
//}