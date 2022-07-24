package id.stefanusdany.data.repository

import java.io.File
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import id.stefanusdany.core.helper.utils.Helper.TAG
import id.stefanusdany.core.helper.utils.wrapEspressoIdlingResource
import id.stefanusdany.data.data.Result
import id.stefanusdany.data.data.local.RemoteMediator
import id.stefanusdany.data.data.local.StoryDatabase
import id.stefanusdany.data.data.remote.BodyLogin
import id.stefanusdany.data.data.remote.BodyRegister
import id.stefanusdany.data.data.remote.response.FileUploadResponse
import id.stefanusdany.data.data.remote.response.ListStoryResponse
import id.stefanusdany.data.data.remote.response.LoginResponse
import id.stefanusdany.data.data.remote.response.LoginResultResponse
import id.stefanusdany.data.data.remote.response.RegisterResponse
import id.stefanusdany.data.data.remote.response.StoryResponse
import id.stefanusdany.data.data.remote.retrofit.ApiService
import id.stefanusdany.data.model.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

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
        file: File,
        description: String,
        lat: Float,
        lon: Float
    ): LiveData<Result<FileUploadResponse>> = liveData {
        emit(Result.Loading)
        try {

            val response = apiService.uploadImage(
                token = token,
                file = MultipartBody.Part.createFormData(
                    PHOTO_FILE,
                    file.name,
                    file.asRequestBody(IMAGE_FILE.toMediaTypeOrNull())
                ),
                description = description.toRequestBody(TEXT_FILE.toMediaTypeOrNull()),
                lat = lat.toString().toRequestBody(TEXT_FILE.toMediaTypeOrNull()),
                lon = lon.toString().toRequestBody(TEXT_FILE.toMediaTypeOrNull())
            )
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        private const val TEXT_FILE = "text/plain"
        private const val IMAGE_FILE = "image/jpeg"
        private const val PHOTO_FILE = "photo"

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