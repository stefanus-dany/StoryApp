package id.stefanusdany.storyapp.ui.maps

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import id.stefanusdany.domain.usecase.auth.AuthUseCase
import id.stefanusdany.domain.usecase.story.StoryUseCase

class MapsViewModel(private val authUseCase: AuthUseCase, private val storyUseCase: StoryUseCase) :
    ViewModel() {

    fun getUserInfo() = LiveDataReactiveStreams.fromPublisher(authUseCase.getUserInfo())

    fun getAllMarkerMaps(token: String, location: Int) =
        LiveDataReactiveStreams.fromPublisher(storyUseCase.getAllMarkerMaps(token, location))

}