package com.example.give.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.give.database.user.User
import com.example.give.database.user.UserDao

@Database(entities = [User::class],version = 1)
abstract class GiveDatabase: RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object{
        @Volatile
        private var INSTANCE: GiveDatabase?=null
        fun getInstance(context: Context): GiveDatabase {
            synchronized(this){
                var instance= INSTANCE
                if(instance == null){
                    instance= Room.databaseBuilder( context.applicationContext,
                        GiveDatabase::class.java,"GiveDatabase")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE =instance
                }
                return instance
            }
        }

    }


}