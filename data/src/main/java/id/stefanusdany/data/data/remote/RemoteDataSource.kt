package id.stefanusdany.data.data.remote

import java.io.File
import android.annotation.SuppressLint
import id.stefanusdany.data.data.remote.response.FileUploadResponse
import id.stefanusdany.data.data.remote.response.ListStoryResponse
import id.stefanusdany.data.data.remote.response.LoginResponse
import id.stefanusdany.data.data.remote.response.RegisterResponse
import id.stefanusdany.data.data.remote.response.StoryResponse
import id.stefanusdany.data.data.remote.retrofit.ApiService
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@SuppressLint("CheckResult")
class RemoteDataSource private constructor(private val apiService: ApiService) {

    fun getAllStories(token: String): Flowable<ApiResponse<List<ListStoryResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<ListStoryResponse>>>()

        apiService.getAllStories(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({
                resultData.onNext(
                    if (it.listStory.isEmpty()) ApiResponse.Empty else ApiResponse.Success(
                        it.listStory
                    )
                )
            }, {
                resultData.onNext(ApiResponse.Error(it.message.toString()))
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getAllMarkerMaps(token: String, location: Int): Flowable<ApiResponse<StoryResponse>> {
        val resultData = PublishSubject.create<ApiResponse<StoryResponse>>()

        apiService.getAllMarkerMaps(token, location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({
                if (it.listStory.isEmpty()) resultData.onNext(ApiResponse.Empty) else resultData.onNext(
                    ApiResponse.Success(it)
                )
            }, {
                resultData.onNext(ApiResponse.Error(it.message.toString()))
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun register(
        name: String,
        email: String,
        password: String
    ): Flowable<ApiResponse<RegisterResponse>> {
        val resultData = PublishSubject.create<ApiResponse<RegisterResponse>>()

        apiService.register(BodyRegister(name, email, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({
                resultData.onNext(ApiResponse.Success(it))
            }, {
                resultData.onNext(ApiResponse.Error(it.message.toString()))
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun login(email: String, password: String): Flowable<ApiResponse<LoginResponse>> {
        val resultData = PublishSubject.create<ApiResponse<LoginResponse>>()

        apiService.login(BodyLogin(email, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({
                resultData.onNext(ApiResponse.Success(it))
            }, {
                resultData.onNext(ApiResponse.Error(it.message.toString()))
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun uploadStory(
        token: String,
        file: File,
        description: String,
        lat: Float,
        lon: Float
    ): Flowable<ApiResponse<FileUploadResponse>> {
        val resultData = PublishSubject.create<ApiResponse<FileUploadResponse>>()
        apiService.uploadImage(
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({
                resultData.onNext(ApiResponse.Success(it))
            }, {
                resultData.onNext(ApiResponse.Error(it.message.toString()))
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    companion object {
        private const val TEXT_FILE = "text/plain"
        private const val IMAGE_FILE = "image/jpeg"
        private const val PHOTO_FILE = "photo"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }
}