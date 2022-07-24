package id.stefanusdany.data.data.remote.retrofit

import id.stefanusdany.data.data.remote.BodyLogin
import id.stefanusdany.data.data.remote.BodyRegister
import id.stefanusdany.data.data.remote.response.FileUploadResponse
import id.stefanusdany.data.data.remote.response.LoginResponse
import id.stefanusdany.data.data.remote.response.RegisterResponse
import id.stefanusdany.data.data.remote.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse

    @GET("stories")
    suspend fun getAllMarkerMaps(
        @Header("Authorization") token: String,
        @Query("location") location: Int
    ): StoryResponse

    @POST("register")
    suspend fun register(
        @Body bodyRegister: BodyRegister
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body bodyLogin: BodyLogin
    ): LoginResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("lon") lon: RequestBody
    ): FileUploadResponse
}