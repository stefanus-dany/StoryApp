package id.stefanusdany.data.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("error")
    val error: Boolean = false,

    @SerializedName("message")
    val message: String = ""
)