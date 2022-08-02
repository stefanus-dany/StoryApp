//package id.stefanusdany.data.repository
//
//import java.io.File
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import id.stefanusdany.data.DataDummy
//import id.stefanusdany.data.MainCoroutineRule
//import id.stefanusdany.data.data.FakeApiService
//import id.stefanusdany.data.data.userPreference.FakeUserPreference
//import id.stefanusdany.data.data.userPreference.IUserPreference
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.test.runBlockingTest
//import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody.Companion.asRequestBody
//import okhttp3.RequestBody.Companion.toRequestBody
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertNotNull
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//class RepositoryTest {
//
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()
//
//    private lateinit var apiService: id.stefanusdany.data.data.remote.retrofit.ApiService
//
//    private lateinit var pref: IUserPreference
//
//    private val file = File("file")
//    private val description = "description"
//        .toRequestBody("text/plain".toMediaType())
//    private val lat = "0.0F"
//        .toRequestBody("text/plain".toMediaType())
//    private val long = "0.0F"
//        .toRequestBody("text/plain".toMediaType())
//    private val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
//    private val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
//        "photo",
//        file.name,
//        requestImageFile
//    )
//
//    @Before
//    fun setUp() {
//        apiService = FakeApiService()
//        pref = FakeUserPreference()
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when save user info response should not null`() = mainCoroutineRule.runBlockingTest {
//        val expectedResponse = DataDummy.generateSaveUserInfoResponse()
//        val actualResponse = pref.getUserInfo().first()
//        assertNotNull(actualResponse)
//        assertEquals(expectedResponse.token, actualResponse.token)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when logout response should not null`() = mainCoroutineRule.runBlockingTest {
//        val expectedResponse = DataDummy.generateLogoutResponse()
//        pref.logout()
//        val actualResponse = pref.getUserInfo().first()
//        assertNotNull(actualResponse)
//        assertEquals(expectedResponse.token, actualResponse.token)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when get user info response should not null`() = mainCoroutineRule.runBlockingTest {
//        val expectedResponse = DataDummy.generateDummyUserInfoResponse()
//        val actualResponse = pref.getUserInfo().first()
//        assertNotNull(actualResponse)
//        assertEquals(expectedResponse.token, actualResponse.token)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when register response should not null`() = mainCoroutineRule.runBlockingTest {
//        val expectedResponse = DataDummy.generateDummyRegisterResponse()
//        val actualResponse =
//            apiService.register(
//                id.stefanusdany.data.data.remote.BodyRegister(
//                    "name",
//                    "email@gmail.com",
//                    "password"
//                )
//            )
//        assertNotNull(actualResponse)
//        assertEquals(expectedResponse.message, actualResponse.message)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when login response should not null`() = mainCoroutineRule.runBlockingTest {
//        val expectedResponse = DataDummy.generateDummyLoginResponse()
//        val actualResponse = apiService.login(
//            id.stefanusdany.data.data.remote.BodyLogin(
//                "email@gmail.com",
//                "password"
//            )
//        )
//        assertNotNull(actualResponse)
//        assertEquals(expectedResponse.message, actualResponse.message)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when get all stories response should not null`() = mainCoroutineRule.runBlockingTest {
//        val expectedResponse = DataDummy.generateDummyStoryResponse()
//        val actualResponse = apiService.getAllStories("token", 1, 5)
//        assertNotNull(actualResponse)
//        assertEquals(expectedResponse.listStory.size, actualResponse.listStory.size)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when getAllMarkerMaps response should not null`() = mainCoroutineRule.runBlockingTest {
//        val expectedResponse = DataDummy.generateDummyStoryResponse()
//        val actualResponse = apiService.getAllMarkerMaps("apiKey", 1)
//        assertNotNull(actualResponse)
//        assertEquals(expectedResponse.listStory.size, actualResponse.listStory.size)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `when upload story response should not null`() = mainCoroutineRule.runBlockingTest {
//        val expectedResponse = DataDummy.generateDummyAddStoryResponse()
//        val actualResponse = apiService.uploadImage("token", imageMultipart, description, lat, long)
//        assertNotNull(actualResponse)
//        assertEquals(expectedResponse.message, actualResponse.message)
//    }
//}