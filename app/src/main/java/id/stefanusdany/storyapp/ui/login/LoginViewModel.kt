package id.stefanusdany.storyapp.ui.login

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import id.stefanusdany.domain.usecase.auth.AuthUseCase

class LoginViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    fun login(email: String, password: String) =
        LiveDataReactiveStreams.fromPublisher(authUseCase.login(email, password))

    fun saveUser(userId: String, userName: String, token: String) =
        authUseCase.saveUser(userId, userName, token)
}