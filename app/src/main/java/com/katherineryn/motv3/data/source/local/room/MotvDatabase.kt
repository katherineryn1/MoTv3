package com.katherineryn.motv3.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.katherineryn.motv3.data.source.local.entity.MovieEntity
import com.katherineryn.motv3.data.source.local.entity.TvShowEntity

@Database(
    entities = [MovieEntity::class, TvShowEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MotvDatabase : RoomDatabase() {
    abstract fun motvDao(): MotvDao

    companion object {
        @Volatile
        private var INSTANCE: MotvDatabase? = null

        fun getInstance(context: Context): MotvDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MotvDatabase::class.java,
                    "Motv.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}