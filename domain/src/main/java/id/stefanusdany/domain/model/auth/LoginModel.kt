package id.stefanusdany.domain.model.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginModel(
    val error: Boolean,
    val message: String,
    val result: LoginResultModel
): Parcelable