package id.stefanusdany.data.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.stefanusdany.data.data.remote.response.ListStoryResponse

@Dao
interface ListStoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<ListStoryResponse>)

    @Query("SELECT * FROM list_story")
    fun getAllStories(): PagingSource<Int, ListStoryResponse>

    @Query("DELETE FROM list_story")
    suspend fun deleteAll()
}