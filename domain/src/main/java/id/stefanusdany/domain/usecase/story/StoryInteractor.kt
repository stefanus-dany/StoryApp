package id.stefanusdany.domain.usecase.story

import java.io.File
import javax.inject.Inject
import id.stefanusdany.core.helper.utils.Result
import id.stefanusdany.domain.model.story.FileUploadModel
import id.stefanusdany.domain.model.story.ListStoryModel
import id.stefanusdany.domain.model.story.StoryModel
import id.stefanusdany.domain.repository.IRepository
import io.reactivex.Flowable

class StoryInteractor @Inject constructor(private val repository: IRepository) : StoryUseCase {
    override fun getAllStories(token: String, location: Int): Flowable<Result<List<ListStoryModel>>> =
        repository.getAllStories(token, location)

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

    override fun getFavoriteStory(): Flowable<List<ListStoryModel>> = repository.getFavoriteStory()

    override fun setFavoriteStory(story: ListStoryModel, newState: Boolean) =
        repository.setFavoriteStory(story, newState)
}