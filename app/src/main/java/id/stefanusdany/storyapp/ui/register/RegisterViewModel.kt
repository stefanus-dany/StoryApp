package id.stefanusdany.storyapp.ui.register

import androidx.lifecycle.ViewModel
import id.stefanusdany.storyapp.repository.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {

    fun register(name: String, email: String, password: String) =
        repository.register(name, email, password)
}