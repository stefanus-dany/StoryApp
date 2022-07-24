package id.stefanusdany.data.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResultResponse(
    @SerializedName("userId")
    val userId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("token")
    val token: String,
)