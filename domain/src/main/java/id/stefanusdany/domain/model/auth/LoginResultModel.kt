package id.stefanusdany.domain.model.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResultModel(
    val userId: String,
    val name: String,
    val token: String
) : Parcelable