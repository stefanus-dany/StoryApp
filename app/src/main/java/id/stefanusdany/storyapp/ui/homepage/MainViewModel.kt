package id.stefanusdany.storyapp.ui.homepage

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import id.stefanusdany.domain.usecase.auth.AuthUseCase
import id.stefanusdany.domain.usecase.story.StoryUseCase

class MainViewModel(private val storyUseCase: StoryUseCase, private val authUseCase: AuthUseCase) :
    ViewModel() {

    fun getUserInfo() = LiveDataReactiveStreams.fromPublisher(authUseCase.getUserInfo())

    fun getAllStories(token: String) =
        LiveDataReactiveStreams.fromPublisher(storyUseCase.getAllStories(token))

    fun logout() = authUseCase.logout()

}