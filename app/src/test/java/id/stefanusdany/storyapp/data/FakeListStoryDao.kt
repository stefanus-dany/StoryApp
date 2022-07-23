//package id.stefanusdany.storyapp.data
//
//import androidx.lifecycle.MutableLiveData
//import androidx.paging.*
//import id.stefanusdany.storyapp.DataDummy
//import id.stefanusdany.storyapp.data.local.ListStoryDao
//import id.stefanusdany.storyapp.data.local.RemoteMediator
//import id.stefanusdany.storyapp.data.remote.response.ListStoryResponse
//import id.stefanusdany.storyapp.ui.homepage.MainAdapter
//import id.stefanusdany.storyapp.ui.homepage.PagedTestDataSources
//import id.stefanusdany.storyapp.ui.homepage.noopListUpdateCallback
//
//class FakeListStoryDao : ListStoryDao {
//
//    private var storyData = listOf<ListStoryResponse>()
//    private val dummyStory = DataDummy.generateDummyListStoryResponse()
//
//    override suspend fun insertStory(story: List<ListStoryResponse>) {
//        storyData = story
//    }
//
//    override fun getAllStories(): PagingSource<Int, ListStoryResponse> {
//        val data = PagedTestDataSources.snapshot(dummyStory)
//        val quote = MutableLiveData<PagingData<ListStoryResponse>>()
//        quote.value = data
//        val ps = PagingData(1, "aa")
//        return ps
//    }
//
//    override suspend fun deleteAll() {
//        storyData = listOf()
//    }
//}