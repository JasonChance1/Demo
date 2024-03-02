package com.example.demo.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demo.MainApplication
import com.example.demo.database.dao.UserDao
import com.example.demo.database.entity.User


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun get(): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    MainApplication.instance(),
                    AppDatabase::class.java,
                    "db"
                ).allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()  // 强制升级，清空数据库
                    .build()
            }
            return instance!!
        }
    }


}