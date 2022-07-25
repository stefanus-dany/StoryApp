package id.stefanusdany.domain.model.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterModel(
    val error: Boolean,
    val message: String,
) : Parcelable