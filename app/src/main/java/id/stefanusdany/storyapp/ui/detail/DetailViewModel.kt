package id.stefanusdany.storyapp.ui.detail

import javax.inject.Inject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.stefanusdany.domain.model.story.ListStoryModel
import id.stefanusdany.domain.usecase.story.StoryUseCase

@HiltViewModel
class DetailViewModel @Inject constructor(private val storyUseCase: StoryUseCase) : ViewModel() {

    fun setFavoriteStory(story: ListStoryModel, newState: Boolean) =
        storyUseCase.setFavoriteStory(story, newState)

}