package id.stefanusdany.storyapp.ui.homepage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import id.stefanusdany.storyapp.DataDummy
import id.stefanusdany.storyapp.MainCoroutineRule
import id.stefanusdany.storyapp.data.remote.response.ListStoryResponse
import id.stefanusdany.storyapp.data.remote.response.LoginResultResponse
import id.stefanusdany.storyapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRules = MainCoroutineRule()

    @Mock
    private lateinit var mainViewModel: MainViewModel

    private val dummyUserInfo = DataDummy.generateDummyUserInfoResponse()
    private val dummyLogout = DataDummy.generateLogoutResponse()

    @Test
    fun `when Get List Story Should Not Null`() = mainCoroutineRules.runBlockingTest {
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTdQYjNXNzM1UXZiY3JhVk4iLCJpYXQiOjE2NTA3NzI5ODB9.jIxrscIU2_Wd82GuPhwjgf-chO3SA_vrPVsZT0H-ZyM"
        val dummyStory = DataDummy.generateDummyListStoryResponse()
        val data = PagedTestDataSources.snapshot(dummyStory)
        val quote = MutableLiveData<PagingData<ListStoryResponse>>()
        quote.value = data

        Mockito.`when`(mainViewModel.getAllStories(token)).thenReturn(quote)
        val actualNews = mainViewModel.getAllStories(token).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = MainAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRules.dispatcher,
            workerDispatcher = mainCoroutineRules.dispatcher,
        )
        differ.submitData(actualNews)

        advanceUntilIdle()
        Mockito.verify(mainViewModel).getAllStories(token)
        assertNotNull(differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
        assertEquals(dummyStory[0].name, differ.snapshot()[0]?.name)
    }

    @Test
    fun `when get user info response should not null`() {
        val expectedResponse = MutableLiveData<LoginResultResponse>()
        expectedResponse.value = dummyUserInfo

        Mockito.`when`(mainViewModel.getUserInfo())
            .thenReturn(expectedResponse)

        val actualResponse = mainViewModel.getUserInfo().getOrAwaitValue()

        assertNotNull(actualResponse)
        assertEquals(expectedResponse.value, actualResponse)

    }

    @Test
    fun `when logout response should not null`() {
        val expectedResponse = MutableLiveData<LoginResultResponse>()
        expectedResponse.value = dummyLogout

        Mockito.`when`(mainViewModel.getUserInfo())
            .thenReturn(expectedResponse)
        val actualResponse = mainViewModel.getUserInfo().getOrAwaitValue()

        assertNotNull(actualResponse)
        assertEquals(expectedResponse.value, actualResponse)

    }
}

class PagedTestDataSources private constructor(private val items: List<ListStoryResponse>) :
    PagingSource<Int, LiveData<List<ListStoryResponse>>>() {
    companion object {
        fun snapshot(items: List<ListStoryResponse>): PagingData<ListStoryResponse> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryResponse>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryResponse>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}