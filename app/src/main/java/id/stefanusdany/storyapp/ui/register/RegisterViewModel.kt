package id.stefanusdany.storyapp.ui.register

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import id.stefanusdany.domain.usecase.auth.AuthUseCase

class RegisterViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    fun register(name: String, email: String, password: String) =
        LiveDataReactiveStreams.fromPublisher(authUseCase.register(name, email, password))
}