package id.stefanusdany.storyapp.ui.addStory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.stefanusdany.storyapp.DataDummy
import id.stefanusdany.storyapp.data.Result
import id.stefanusdany.storyapp.data.remote.response.FileUploadResponse
import id.stefanusdany.storyapp.getOrAwaitValue
import id.stefanusdany.storyapp.repository.Repository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository
    private lateinit var addStoryViewModel: AddStoryViewModel
    private val dummyAddStory = DataDummy.generateDummyAddStoryResponse()
    private val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTdQYjNXNzM1UXZiY3JhVk4iLCJpYXQiOjE2NTA3NzI5ODB9.jIxrscIU2_Wd82GuPhwjgf-chO3SA_vrPVsZT0H-ZyM"
    private val file = File("file")
    private val description = "description"
        .toRequestBody("text/plain".toMediaType())
    private val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
    private val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
        "photo",
        file.name,
        requestImageFile
    )


    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(repository)
    }

//    @Test
//    fun `when upload story response should not null and return success`() {
//        val expectedResponse = MutableLiveData<Result<FileUploadResponse>>()
//        expectedResponse.value = Result.Success(dummyAddStory)
//
//        Mockito.`when`(addStoryViewModel.uploadStory(token, imageMultipart, description))
//            .thenReturn(expectedResponse)
//
//        val actualResponse =
//            addStoryViewModel.uploadStory(token, imageMultipart, description).getOrAwaitValue()
//
//        Mockito.verify(repository).uploadStory(token, imageMultipart, description)
//        assertNotNull(actualResponse)
//        assertTrue(actualResponse is Result.Success)
//    }

//    @Test
//    fun `when upload story response error should return error`() {
//        val expected = MutableLiveData<Result<FileUploadResponse>>()
//        expected.value = Result.Error("Error")
//        Mockito.`when`(addStoryViewModel.uploadStory(token, imageMultipart, description))
//            .thenReturn(expected)
//        val actualResponse =
//            addStoryViewModel.uploadStory(token, imageMultipart, description).getOrAwaitValue()
//        Mockito.verify(repository).uploadStory(token, imageMultipart, description)
//        assertNotNull(actualResponse)
//        assertTrue(actualResponse is Result.Error)
//    }

}