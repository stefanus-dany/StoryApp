package id.stefanusdany.favorite.ui

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import id.stefanusdany.domain.usecase.story.StoryUseCase

class FavoriteViewModel(private val storyUseCase: StoryUseCase) : ViewModel() {

    fun getFavoriteStory() = LiveDataReactiveStreams.fromPublisher(storyUseCase.getFavoriteStory())

}