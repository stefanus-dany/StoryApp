package id.stefanusdany.storyapp.ui.homepage

import javax.inject.Inject
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.stefanusdany.domain.usecase.auth.AuthUseCase
import id.stefanusdany.domain.usecase.story.StoryUseCase

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storyUseCase: StoryUseCase,
    private val authUseCase: AuthUseCase
) :
    ViewModel() {

    fun getUserInfo() = LiveDataReactiveStreams.fromPublisher(authUseCase.getUserInfo())

    fun getAllStories(token: String, location: Int) =
        LiveDataReactiveStreams.fromPublisher(storyUseCase.getAllStories(token, location))

    fun logout() = authUseCase.logout()

}