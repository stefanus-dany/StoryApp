package id.stefanusdany.storyapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.stefanusdany.storyapp.data.remote.response.LoginResultResponse
import id.stefanusdany.storyapp.repository.Repository

class MapsViewModel(private val repository: Repository) : ViewModel() {

    fun getUserInfo(): LiveData<LoginResultResponse> = repository.getUserInfo().asLiveData()

    fun getAllMarkerMaps(token: String, location: Int) =
        repository.getAllMarkerMaps(token, location)

}