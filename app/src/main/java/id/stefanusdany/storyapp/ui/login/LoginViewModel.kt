package id.stefanusdany.storyapp.ui.login

import androidx.lifecycle.ViewModel
import id.stefanusdany.storyapp.repository.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password)

    fun saveUser(userId: String, userName: String, token: String) {
        repository.saveUser(userId, userName, token)
    }
}