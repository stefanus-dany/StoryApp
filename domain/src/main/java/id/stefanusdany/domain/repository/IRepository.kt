package id.stefanusdany.domain.repository

import java.io.File
import id.stefanusdany.core.helper.utils.Result
import id.stefanusdany.domain.model.auth.LoginModel
import id.stefanusdany.domain.model.auth.LoginResultModel
import id.stefanusdany.domain.model.auth.RegisterModel
import id.stefanusdany.domain.model.story.FileUploadModel
import id.stefanusdany.domain.model.story.ListStoryModel
import id.stefanusdany.domain.model.story.StoryModel
import io.reactivex.Flowable

interface IRepository {

    fun saveUser(userId: String, userName: String, token: String)

    fun logout()

    fun getUserInfo(): Flowable<LoginResultModel>

    fun register(
        name: String,
        email: String,
        password: String
    ): Flowable<Result<RegisterModel>>

    fun login(email: String, password: String): Flowable<Result<LoginModel>>

    fun getAllStories(token: String): Flowable<Result<List<ListStoryModel>>>

    fun getAllMarkerMaps(token: String, location: Int): Flowable<Result<StoryModel>>

    fun uploadStory(
        token: String,
        file: File,
        description: String,
        lat: Float,
        lon: Float
    ): Flowable<Result<FileUploadModel>>

    fun getFavoriteStory(): Flowable<List<ListStoryModel>>

    fun setFavoriteStory(story: ListStoryModel, newState: Boolean)

}