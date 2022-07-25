package id.stefanusdany.storyapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.stefanusdany.domain.usecase.auth.AuthUseCase
import id.stefanusdany.domain.usecase.story.StoryUseCase
import id.stefanusdany.storyapp.di.Injection
import id.stefanusdany.storyapp.ui.addStory.AddStoryViewModel
import id.stefanusdany.storyapp.ui.homepage.MainViewModel
import id.stefanusdany.storyapp.ui.login.LoginViewModel
import id.stefanusdany.storyapp.ui.maps.MapsViewModel
import id.stefanusdany.storyapp.ui.register.RegisterViewModel

class ViewModelFactory(
    private val authUseCase: AuthUseCase,
    private val storyUseCase: StoryUseCase
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authUseCase) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authUseCase) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(storyUseCase, authUseCase) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(storyUseCase) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(authUseCase, storyUseCase) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideAuthUseCase(context),
                    Injection.provideStoryUseCase(context)
                )
            }.also { instance = it }
    }
}