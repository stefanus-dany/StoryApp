package id.stefanusdany.data.data.local

import id.stefanusdany.data.data.local.entity.ListStoryEntity
import id.stefanusdany.data.data.remote.response.LoginResultResponse
import id.stefanusdany.data.model.UserPreference
import io.reactivex.Flowable

class LocalDataSource private constructor(
    private val listStoryDao: ListStoryDao,
    private val pref: UserPreference
) {

    fun insertStory(story: List<ListStoryEntity>) = listStoryDao.insertStory(story)

    fun getAllStories(): Flowable<List<ListStoryEntity>> = listStoryDao.getAllStories()

    fun deleteAll() = listStoryDao.deleteAll()

    fun saveUser(userId: String, userName: String, token: String) =
        pref.login(userId, userName, token)

    fun logout() = pref.logout()

    fun getUserInfo(): Flowable<LoginResultResponse> = pref.getUserInfo()

    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(
            listStoryDao: ListStoryDao,
            userPreference: UserPreference
        ): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(listStoryDao, userPreference)
            }
    }
}