package id.stefanusdany.domain.model.story

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileUploadModel(
    val error: Boolean,
    val message: String
) : Parcelable