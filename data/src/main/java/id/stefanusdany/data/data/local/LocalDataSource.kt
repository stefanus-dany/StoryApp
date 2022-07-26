package id.stefanusdany.data.data.local

import javax.inject.Inject
import javax.inject.Singleton
import id.stefanusdany.data.data.local.entity.ListStoryEntity
import id.stefanusdany.data.data.remote.response.LoginResultResponse
import id.stefanusdany.data.datastore.UserPreference
import io.reactivex.Flowable

@Singleton
class LocalDataSource @Inject constructor(
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

    fun getFavoriteStory(): Flowable<List<ListStoryEntity>> = listStoryDao.getFavoriteStory()

    fun setFavoriteStory(story: ListStoryEntity, newState: Boolean) {
        story.isFavorite = newState
        listStoryDao.updateFavoriteStory(story)
    }
}