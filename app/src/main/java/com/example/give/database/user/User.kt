package com.example.give.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User (
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name="Username")
    val username:String,
    @ColumnInfo(name="Password")
    val password:String
)