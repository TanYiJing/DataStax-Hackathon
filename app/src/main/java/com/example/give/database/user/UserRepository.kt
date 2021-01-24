package com.example.give.database.user

class UserRepository( private val userDao: UserDao){




     fun validateUser(username:String,password:String):Int{
        return userDao.validateUser(username,password)

    }

    suspend fun insertUser(user: User){
        userDao.insertUser(user)
    }
     fun getTotalUser():Int{
        return userDao.getTotalUser()
    }


}