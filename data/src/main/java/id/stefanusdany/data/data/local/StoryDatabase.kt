package id.stefanusdany.data.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.stefanusdany.data.data.local.entity.ListStoryEntity

@Database(
    entities = [ListStoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun listStoryDao(): ListStoryDao

}