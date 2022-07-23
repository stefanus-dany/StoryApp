package id.stefanusdany.storyapp.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.stefanusdany.storyapp.DataDummy
import id.stefanusdany.storyapp.data.Result
import id.stefanusdany.storyapp.data.remote.response.StoryResponse
import id.stefanusdany.storyapp.getOrAwaitValue
import id.stefanusdany.storyapp.repository.Repository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyStory = DataDummy.generateDummyStoryResponse()
    private val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTdQYjNXNzM1UXZiY3JhVk4iLCJpYXQiOjE2NTA3NzI5ODB9.jIxrscIU2_Wd82GuPhwjgf-chO3SA_vrPVsZT0H-ZyM"

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(repository)
    }

    @Test
    fun `when get all marker maps response should not null and return success`() {
        val expectedResponse = MutableLiveData<Result<StoryResponse>>()
        expectedResponse.value = Result.Success(dummyStory)

        Mockito.`when`(mapsViewModel.getAllMarkerMaps(token, 1))
            .thenReturn(expectedResponse)

        val actualResponse = mapsViewModel.getAllMarkerMaps(token, 1).getOrAwaitValue()

        Mockito.verify(repository).getAllMarkerMaps(token, 1)
        assertNotNull(actualResponse)
        assertTrue(actualResponse is Result.Success)
        assertEquals(
            dummyStory.listStory.size,
            (actualResponse as Result.Success).data.listStory.size
        )
    }

    @Test
    fun `when get all marker maps response error should return error`() {
        val expected = MutableLiveData<Result<StoryResponse>>()
        expected.value = Result.Error("Error")
        Mockito.`when`(mapsViewModel.getAllMarkerMaps(token, 1)).thenReturn(expected)
        val actualResponse = mapsViewModel.getAllMarkerMaps(token, 1).getOrAwaitValue()
        Mockito.verify(repository).getAllMarkerMaps(token, 1)
        assertNotNull(actualResponse)
        assertTrue(actualResponse is Result.Error)
    }

}