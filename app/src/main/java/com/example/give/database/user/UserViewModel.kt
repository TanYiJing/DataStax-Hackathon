package com.example.give.database.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.give.database.GiveDatabase
import kotlinx.coroutines.*

class UserViewModel (application: Application):AndroidViewModel(application){
    private val userRepository: UserRepository
    private var viewModelJob = Job()
    public val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

init {
    val userDao = GiveDatabase.getInstance(application).userDao()
    userRepository= UserRepository(userDao)
}
    fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insertUser(user)
    }

suspend fun  getTotalUser():Int{

    return withContext(Dispatchers.IO) {

        val totalUser:Int= userRepository.getTotalUser()
        totalUser
    }
}

    suspend fun validateUser(username:String,password:String):Int{
        return withContext(Dispatchers.IO) {

        val boolean :Int =userRepository.validateUser(username,password)
                boolean
        }
    }

}