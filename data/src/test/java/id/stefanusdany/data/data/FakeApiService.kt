//package id.stefanusdany.data.data
//
//import id.stefanusdany.data.DataDummy
//import id.stefanusdany.data.data.remote.response.FileUploadResponse
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//
//class FakeApiService : id.stefanusdany.data.data.remote.retrofit.ApiService {
//
//    private val dummyRegister = DataDummy.generateDummyRegisterResponse()
//    private val dummyLogin = DataDummy.generateDummyLoginResponse()
//    private val dummyAddStory = DataDummy.generateDummyAddStoryResponse()
//    private val dummyStory = DataDummy.generateDummyStoryResponse()
//
//    override suspend fun getAllStories(token: String, page: Int, size: Int) = dummyStory
//
//    override suspend fun getAllMarkerMaps(token: String, location: Int) = dummyStory
//
//    override suspend fun register(bodyRegister: id.stefanusdany.data.data.remote.BodyRegister) =
//        dummyRegister
//
//    override suspend fun login(bodyLogin: id.stefanusdany.data.data.remote.BodyLogin) = dummyLogin
//
//    override suspend fun uploadImage(
//        token: String,
//        file: MultipartBody.Part,
//        description: RequestBody,
//        lat: RequestBody,
//        lon: RequestBody
//    ): FileUploadResponse = dummyAddStory
//}