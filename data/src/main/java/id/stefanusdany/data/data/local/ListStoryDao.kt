package id.stefanusdany.data.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.stefanusdany.data.data.local.entity.ListStoryEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ListStoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStory(story: List<ListStoryEntity>): Completable

    @Query("SELECT * FROM list_story")
    fun getAllStories(): Flowable<List<ListStoryEntity>>

    @Query("DELETE FROM list_story")
    fun deleteAll(): Completable

    @Query("SELECT * FROM list_story where isFavorite = 1")
    fun getFavoriteStory(): Flowable<List<ListStoryEntity>>

    @Update
    fun updateFavoriteStory(story: ListStoryEntity)
}