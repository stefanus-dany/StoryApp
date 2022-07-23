package id.stefanusdany.storyapp.ui.addStory

import androidx.lifecycle.ViewModel
import id.stefanusdany.storyapp.repository.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: Repository) : ViewModel() {

    fun uploadStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody,
        lon: RequestBody
    ) = repository.uploadStory(token = token, file = file, description = description, lat = lat, lon = lon)
}