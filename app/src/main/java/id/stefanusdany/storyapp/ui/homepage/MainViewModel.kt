package id.stefanusdany.storyapp.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.stefanusdany.storyapp.data.remote.response.ListStoryResponse
import id.stefanusdany.storyapp.data.remote.response.LoginResultResponse
import id.stefanusdany.storyapp.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

    fun getUserInfo(): LiveData<LoginResultResponse> = repository.getUserInfo().asLiveData()

    fun getAllStories(token: String): LiveData<PagingData<ListStoryResponse>> =
        repository.getAllStories(token).cachedIn(viewModelScope)

    fun logout() {
        repository.logout()
    }

}