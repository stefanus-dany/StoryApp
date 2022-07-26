package id.stefanusdany.data.di

import dagger.Binds
import dagger.Module
import id.stefanusdany.data.data.repository.Repository
import id.stefanusdany.domain.repository.IRepository

@Module(includes = [NetworkModule::class, StoryDatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository: Repository): IRepository
}