package id.stefanusdany.storyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import id.stefanusdany.storyapp.data.Result
import id.stefanusdany.storyapp.data.local.RemoteMediator
import id.stefanusdany.storyapp.data.local.StoryDatabase
import id.stefanusdany.storyapp.data.remote.BodyLogin
import id.stefanusdany.storyapp.data.remote.BodyRegister
import id.stefanusdany.storyapp.data.remote.response.*
import id.stefanusdany.storyapp.data.remote.retrofit.ApiService
import id.stefanusdany.storyapp.helper.Helper.TAG
import id.stefanusdany.storyapp.helper.wrapEspressoIdlingResource
import id.stefanusdany.storyapp.model.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService,
    private val pref: UserPreference
) {
    fun saveUser(userId: String, userName: String, token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            pref.login(userId, userName, token)
        }
    }

    fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            pref.logout()
        }
    }

    fun getUserInfo(): Flow<LoginResultResponse> = pref.getUserInfo()

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(BodyRegister(name, email, password))
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(BodyLogin(email, password))
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllStories(token: String): LiveData<PagingData<ListStoryResponse>> {
        wrapEspressoIdlingResource {
            @OptIn(ExperimentalPagingApi::class)
            return Pager(
                config = PagingConfig(
                    pageSize = 5
                ),
                remoteMediator = RemoteMediator(storyDatabase, apiService, token),
                pagingSourceFactory = {
                    storyDatabase.listStoryDao().getAllStories()
                }
            ).liveData
        }
    }

    fun getAllMarkerMaps(token: String, location: Int): LiveData<Result<StoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllMarkerMaps(token, location)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun uploadStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody,
        lon: RequestBody
    ): LiveData<Result<FileUploadResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.uploadImage(token, file, description, lat, lon)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            storyDatabase: StoryDatabase,
            apiService: ApiService,
            pref: UserPreference
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(storyDatabase, apiService, pref)
            }.also { instance = it }
    }
}