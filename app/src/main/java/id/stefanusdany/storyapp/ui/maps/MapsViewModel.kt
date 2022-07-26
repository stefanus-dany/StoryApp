package id.stefanusdany.storyapp.ui.maps

import javax.inject.Inject
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.stefanusdany.domain.usecase.auth.AuthUseCase
import id.stefanusdany.domain.usecase.story.StoryUseCase

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val storyUseCase: StoryUseCase
) :
    ViewModel() {

    fun getUserInfo() = LiveDataReactiveStreams.fromPublisher(authUseCase.getUserInfo())

    fun getAllMarkerMaps(token: String, location: Int) =
        LiveDataReactiveStreams.fromPublisher(storyUseCase.getAllMarkerMaps(token, location))

}