package id.stefanusdany.favorite

import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.stefanusdany.domain.usecase.story.StoryUseCase
import id.stefanusdany.favorite.ui.FavoriteViewModel

class ViewModelFactory @Inject constructor(private val storyUseCase: StoryUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(storyUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}