package com.calyrsoft.ucbp1.features.movie.data.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.calyrsoft.ucbp1.features.movie.data.database.dao.MovieDao
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1) // version inicial
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao  // <-- DAO para movies


    companion object {
        @Volatile
        private var Instance: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    "movie_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
