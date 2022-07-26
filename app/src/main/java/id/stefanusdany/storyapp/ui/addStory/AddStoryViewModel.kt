package id.stefanusdany.storyapp.ui.addStory

import java.io.File
import javax.inject.Inject
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.stefanusdany.domain.usecase.story.StoryUseCase

@HiltViewModel
class AddStoryViewModel @Inject constructor(private val storyUseCase: StoryUseCase) : ViewModel() {

    fun uploadStory(
        token: String,
        file: File,
        description: String,
        lat: Float,
        lon: Float
    ) = LiveDataReactiveStreams.fromPublisher(storyUseCase.uploadStory(
        token = token,
        file = file,
        description = description,
        lat = lat,
        lon = lon
    ))
}