package id.stefanusdany.storyapp.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.stefanusdany.domain.usecase.story.StoryUseCase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun storyUseCase(): StoryUseCase
}