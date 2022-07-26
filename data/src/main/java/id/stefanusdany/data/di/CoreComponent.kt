package id.stefanusdany.data.di

import javax.inject.Singleton
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.stefanusdany.data.datastore.IUserPreference
import id.stefanusdany.domain.repository.IRepository

@Singleton
@Component(
    modules = [RepositoryModule::class, DatastoreModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideRepository(): IRepository
    fun provideDatastore(): IUserPreference
}