package id.stefanusdany.storyapp.di

import dagger.Binds
import dagger.Module
import id.stefanusdany.domain.usecase.auth.AuthInteractor
import id.stefanusdany.domain.usecase.auth.AuthUseCase
import id.stefanusdany.domain.usecase.story.StoryInteractor
import id.stefanusdany.domain.usecase.story.StoryUseCase

@Module
abstract class AppModule {

    @Binds
    abstract fun provideAuthUseCase(authInteractor: AuthInteractor): AuthUseCase

    @Binds
    abstract fun provideStoryUseCase(storyInteractor: StoryInteractor): StoryUseCase
}