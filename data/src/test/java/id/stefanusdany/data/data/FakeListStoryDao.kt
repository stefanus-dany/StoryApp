//package id.stefanusdany.storyapp.data
//
//import androidx.lifecycle.MutableLiveData
//import androidx.paging.*
//import id.stefanusdany.data.DataDummy
//import id.stefanusdany.data.data.local.ListStoryDao
//import id.stefanusdany.data.data.remote.response.ListStoryResponse
//import id.stefanusdany.storyapp.ui.homepage.PagedTestDataSources
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
//        return PagingData(1, "aa")
//    }
//
//    override suspend fun deleteAll() {
//        storyData = listOf()
//    }
//}