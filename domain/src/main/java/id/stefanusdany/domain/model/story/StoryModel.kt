package id.stefanusdany.domain.model.story

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryModel(
    val error: Boolean,
    val message: String,
    val listStory: List<ListStoryModel>
) : Parcelable