package id.stefanusdany.storyapp.di

import javax.inject.Singleton
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.stefanusdany.domain.usecase.auth.AuthInteractor
import id.stefanusdany.domain.usecase.auth.AuthUseCase
import id.stefanusdany.domain.usecase.story.StoryInteractor
import id.stefanusdany.domain.usecase.story.StoryUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideAuthUseCase(authInteractor: AuthInteractor): AuthUseCase

    @Binds
    @Singleton
    abstract fun provideStoryUseCase(storyInteractor: StoryInteractor): StoryUseCase
}