package id.stefanusdany.storyapp.ui.register

import javax.inject.Inject
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.stefanusdany.domain.usecase.auth.AuthUseCase

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {

    fun register(name: String, email: String, password: String) =
        LiveDataReactiveStreams.fromPublisher(authUseCase.register(name, email, password))
}