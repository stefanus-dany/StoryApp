package id.stefanusdany.data.mapper

import id.stefanusdany.data.data.local.entity.ListStoryEntity
import id.stefanusdany.data.data.remote.response.FileUploadResponse
import id.stefanusdany.data.data.remote.response.ListStoryResponse
import id.stefanusdany.data.data.remote.response.StoryResponse
import id.stefanusdany.domain.model.story.FileUploadModel
import id.stefanusdany.domain.model.story.ListStoryModel
import id.stefanusdany.domain.model.story.StoryModel

object StoryMapper {

    fun mapListStoryEntityToModel(input: List<ListStoryEntity>): List<ListStoryModel> {
        val listStoryModel = ArrayList<ListStoryModel>()
        input.map { entities ->
            listStoryModel.add(
                ListStoryModel(
                    id = entities.id,
                    name = entities.name,
                    description = entities.description,
                    photoUrl = entities.photoUrl,
                    createdAt = entities.createdAt,
                    lat = entities.lat,
                    lon = entities.lon
                )
            )
        }
        return listStoryModel
    }

    fun mapListStoryResponseToEntity(input: List<ListStoryResponse>): List<ListStoryEntity> {
        val listStoryEntity = ArrayList<ListStoryEntity>()
        input.map { entities ->
            listStoryEntity.add(
                ListStoryEntity(
                    id = entities.id,
                    name = entities.name,
                    description = entities.description,
                    photoUrl = entities.photoUrl,
                    createdAt = entities.createdAt,
                    lat = entities.lat,
                    lon = entities.lon
                )
            )
        }
        return listStoryEntity
    }

    private fun mapListStoryResponseToModel(input: List<ListStoryResponse>): List<ListStoryModel> {
        val listStoryModel = ArrayList<ListStoryModel>()
        input.map { entities ->
            listStoryModel.add(
                ListStoryModel(
                    id = entities.id,
                    name = entities.name,
                    description = entities.description,
                    photoUrl = entities.photoUrl,
                    createdAt = entities.createdAt,
                    lat = entities.lat,
                    lon = entities.lon
                )
            )
        }
        return listStoryModel
    }

    fun fromStoryResponseToModel(input: StoryResponse): StoryModel =
        StoryModel(
            error = input.error,
            message = input.message,
            listStory = mapListStoryResponseToModel(input.listStory)
        )

    fun fromFileUploadResponseToModel(input: FileUploadResponse): FileUploadModel =
        FileUploadModel(
            error = input.error,
            message = input.message
        )
}