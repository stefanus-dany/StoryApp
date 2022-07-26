package id.stefanusdany.data.data.repository

import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import android.annotation.SuppressLint
import id.stefanusdany.core.helper.utils.AppExecutors
import id.stefanusdany.core.helper.utils.Result
import id.stefanusdany.data.data.NetworkBoundResource
import id.stefanusdany.data.data.local.LocalDataSource
import id.stefanusdany.data.data.remote.ApiResponse
import id.stefanusdany.data.data.remote.RemoteDataSource
import id.stefanusdany.data.data.remote.response.ListStoryResponse
import id.stefanusdany.data.mapper.AuthMapper
import id.stefanusdany.data.mapper.StoryMapper
import id.stefanusdany.data.mapper.StoryMapper.fromFileUploadResponseToModel
import id.stefanusdany.data.mapper.StoryMapper.fromStoryResponseToModel
import id.stefanusdany.domain.model.auth.LoginModel
import id.stefanusdany.domain.model.auth.LoginResultModel
import id.stefanusdany.domain.model.auth.RegisterModel
import id.stefanusdany.domain.model.story.FileUploadModel
import id.stefanusdany.domain.model.story.ListStoryModel
import id.stefanusdany.domain.model.story.StoryModel
import id.stefanusdany.domain.repository.IRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

@SuppressLint("CheckResult")
@Singleton
class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : IRepository {
    override fun saveUser(userId: String, userName: String, token: String) =
        appExecutors.diskIO().execute { localDataSource.saveUser(userId, userName, token) }

    override fun logout() = appExecutors.diskIO().execute { localDataSource.logout() }

    override fun getUserInfo(): Flowable<LoginResultModel> = localDataSource.getUserInfo().map {
        AuthMapper.fromLoginResultResponseToModel(it)
    }

    override fun register(
        name: String,
        email: String,
        password: String
    ): Flowable<Result<RegisterModel>> {
        val result = PublishSubject.create<Result<RegisterModel>>()
        result.onNext(Result.Loading)
        remoteDataSource.register(name, email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { response ->
                when (response) {
                    is ApiResponse.Success -> result.onNext(
                        Result.Success(
                            AuthMapper.fromRegisterResponseToModel(
                                response.data
                            )
                        )
                    )
                    is ApiResponse.Error -> result.onNext(Result.Error(response.errorMessage))
                    is ApiResponse.Empty -> {}
                }
            }
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun login(email: String, password: String): Flowable<Result<LoginModel>> {
        val result = PublishSubject.create<Result<LoginModel>>()
        result.onNext(Result.Loading)
        remoteDataSource.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { response ->
                when (response) {
                    is ApiResponse.Success -> result.onNext(
                        Result.Success(
                            AuthMapper.fromLoginResponseToModel(response.data)
                        )
                    )
                    is ApiResponse.Error -> result.onNext(Result.Error(response.errorMessage))
                    is ApiResponse.Empty -> {}
                }
            }
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun getAllStories(token: String): Flowable<Result<List<ListStoryModel>>> =
        object : NetworkBoundResource<List<ListStoryModel>, List<ListStoryResponse>>() {
            override fun loadFromDB(): Flowable<List<ListStoryModel>> {
                return localDataSource.getAllStories().map {
                    StoryMapper.mapListStoryEntityToModel(it)
                }
            }

            override fun shouldFetch(data: List<ListStoryModel>?): Boolean = true

            override fun createCall(): Flowable<ApiResponse<List<ListStoryResponse>>> =
                remoteDataSource.getAllStories(token)

            override fun saveCallResult(data: List<ListStoryResponse>) {
                localDataSource.deleteAll()
                val storyList = StoryMapper.mapListStoryResponseToEntity(data)
                localDataSource.insertStory(storyList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }

        }.asFlowable()

    override fun getAllMarkerMaps(token: String, location: Int): Flowable<Result<StoryModel>> {
        val result = PublishSubject.create<Result<StoryModel>>()
        result.onNext(Result.Loading)
        remoteDataSource.getAllMarkerMaps(token, location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { response ->
                when (response) {
                    is ApiResponse.Success -> result.onNext(
                        Result.Success(
                            fromStoryResponseToModel(response.data)
                        )
                    )
                    is ApiResponse.Error -> result.onNext(Result.Error(response.errorMessage))
                    is ApiResponse.Empty -> {}
                }
            }
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun uploadStory(
        token: String,
        file: File,
        description: String,
        lat: Float,
        lon: Float
    ): Flowable<Result<FileUploadModel>> {
        val result = PublishSubject.create<Result<FileUploadModel>>()
        result.onNext(Result.Loading)
        remoteDataSource.uploadStory(token, file, description, lat, lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { response ->
                when (response) {
                    is ApiResponse.Success -> result.onNext(
                        Result.Success(
                            fromFileUploadResponseToModel(response.data)
                        )
                    )
                    is ApiResponse.Error -> result.onNext(Result.Error(response.errorMessage))
                    is ApiResponse.Empty -> {}
                }
            }
        return result.toFlowable(BackpressureStrategy.BUFFER)

    }

    override fun getFavoriteStory(): Flowable<List<ListStoryModel>> =
        localDataSource.getFavoriteStory().map {
            StoryMapper.mapListStoryEntityToModel(it)
        }

    override fun setFavoriteStory(story: ListStoryModel, newState: Boolean) =
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteStory(StoryMapper.mapStoryModelToEntity(story), newState)
        }
}