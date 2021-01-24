package com.example.give.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("Select count(User.id) From User where Username=:username and Password = :password")
    fun validateUser(username:String,password:String):Int

    @Query("Select count(User.id) from User")
    fun getTotalUser():Int
}