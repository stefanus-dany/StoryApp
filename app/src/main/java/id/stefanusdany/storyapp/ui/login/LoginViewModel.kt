package id.stefanusdany.storyapp.ui.login

import javax.inject.Inject
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.stefanusdany.domain.usecase.auth.AuthUseCase

@HiltViewModel
class LoginViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {

    fun login(email: String, password: String) =
        LiveDataReactiveStreams.fromPublisher(authUseCase.login(email, password))

    fun saveUser(userId: String, userName: String, token: String) =
        authUseCase.saveUser(userId, userName, token)
}