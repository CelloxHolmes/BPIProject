package com.bpi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bpi.dao.BpiDao
import com.bpi.model.Bpi

@Database(entities = [Bpi::class], version = 1, exportSchema = false)
abstract class BpiDatabase : RoomDatabase() {

    abstract fun bpiDao(): BpiDao

    companion object {
        private var instance: BpiDatabase? = null
        private const val DB_NAME = "bpi.db"

        fun getInstance(context: Context): BpiDatabase {
            if (instance == null) {
                synchronized(BpiDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(context.applicationContext, BpiDatabase::class.java, DB_NAME)
                            .createFromAsset("databases/bpi.db")
                            .build()
                    }
                }
            }

            return instance!!
        }
    }
}

