package id.stefanusdany.storyapp.ui.addStory

import java.io.File
import androidx.lifecycle.ViewModel

class AddStoryViewModel(private val repository: id.stefanusdany.data.repository.Repository) :
    ViewModel() {

    fun uploadStory(
        token: String,
        file: File,
        description: String,
        lat: Float,
        lon: Float
    ) = repository.uploadStory(
        token = token,
        file = file,
        description = description,
        lat = lat,
        lon = lon
    )
}