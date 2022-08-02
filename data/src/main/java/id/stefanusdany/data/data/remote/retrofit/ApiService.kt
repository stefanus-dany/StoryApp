package id.stefanusdany.data.data.remote.retrofit

import id.stefanusdany.data.data.remote.response.FileUploadResponse
import id.stefanusdany.data.data.remote.response.LoginResponse
import id.stefanusdany.data.data.remote.response.RegisterResponse
import id.stefanusdany.data.data.remote.response.StoryResponse
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @GET("stories")
    fun getAllStories(
        @Header("Authorization") token: String,
        @Query("location") location: Int
    ): Flowable<StoryResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Flowable<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Flowable<LoginResponse>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("lon") lon: RequestBody
    ): Flowable<FileUploadResponse>
}