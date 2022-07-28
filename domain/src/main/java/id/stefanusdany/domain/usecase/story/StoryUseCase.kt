package id.stefanusdany.domain.usecase.story

import java.io.File
import id.stefanusdany.core.helper.utils.Result
import id.stefanusdany.domain.model.story.FileUploadModel
import id.stefanusdany.domain.model.story.ListStoryModel
import id.stefanusdany.domain.model.story.StoryModel
import io.reactivex.Flowable

interface StoryUseCase {

    fun getAllStories(token: String, location: Int): Flowable<Result<List<ListStoryModel>>>

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