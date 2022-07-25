package id.stefanusdany.domain.usecase.story

import java.io.File
import id.stefanusdany.core.helper.utils.Result
import id.stefanusdany.domain.model.story.FileUploadModel
import id.stefanusdany.domain.model.story.ListStoryModel
import id.stefanusdany.domain.model.story.StoryModel
import id.stefanusdany.domain.repository.IRepository
import io.reactivex.Flowable

class StoryInteractor(private val repository: IRepository) : StoryUseCase {
    override fun getAllStories(token: String): Flowable<Result<List<ListStoryModel>>> =
        repository.getAllStories(token)

    override fun getAllMarkerMaps(token: String, location: Int): Flowable<Result<StoryModel>> =
        repository.getAllMarkerMaps(token, location)

    override fun uploadStory(
        token: String,
        file: File,
        description: String,
        lat: Float,
        lon: Float
    ): Flowable<Result<FileUploadModel>> =
        repository.uploadStory(token, file, description, lat, lon)

}