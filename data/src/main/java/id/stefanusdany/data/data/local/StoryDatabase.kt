package id.stefanusdany.data.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.stefanusdany.data.data.local.entity.ListStoryEntity

@Database(
    entities = [ListStoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun listStoryDao(): ListStoryDao

//    companion object {
//        @Volatile
//        private var INSTANCE: StoryDatabase? = null
//
//        @JvmStatic
//        fun getDatabase(context: Context): StoryDatabase {
//            return INSTANCE ?: synchronized(this) {
//                INSTANCE ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    StoryDatabase::class.java, "story_database.db"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    .also { INSTANCE = it }
//            }
//        }
//    }
}