package id.stefanusdany.data.di

import javax.inject.Singleton
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.stefanusdany.domain.repository.IRepository

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    //    fun provideDatastore(): IUserPreference
    fun provideRepository(): IRepository
}