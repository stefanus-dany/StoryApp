package id.stefanusdany.storyapp.data

import id.stefanusdany.storyapp.DataDummy
import id.stefanusdany.storyapp.data.remote.BodyLogin
import id.stefanusdany.storyapp.data.remote.BodyRegister
import id.stefanusdany.storyapp.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiService : ApiService {

    private val dummyRegister = DataDummy.generateDummyRegisterResponse()
    private val dummyLogin = DataDummy.generateDummyLoginResponse()
    private val dummyAddStory = DataDummy.generateDummyAddStoryResponse()
    private val dummyStory = DataDummy.generateDummyStoryResponse()

    override suspend fun getAllStories(token: String, page: Int, size: Int) = dummyStory

    override suspend fun getAllMarkerMaps(token: String, location: Int) = dummyStory

    override suspend fun register(bodyRegister: BodyRegister) = dummyRegister

    override suspend fun login(bodyLogin: BodyLogin) = dummyLogin

    override suspend fun uploadImage(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ) = dummyAddStory
}