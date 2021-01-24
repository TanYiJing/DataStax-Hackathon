package com.example.give

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaDrm
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.give.database.user.UserViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
//
//        userViewModel.uiScope.launch{
//            textView6.text = userViewModel.getTotalUser().toString()
//        }

        buttonLogin.setOnClickListener {
            //            userViewModel.uiScope.launch {
//                val boolean :Int = userViewModel.validateUser(
//                    editTextLogUsername.text.toString(),
//                    editTextLogPassword.text.toString()
//                )
//                if(boolean==1)
//                {
//                    textView6.text="correct"
//
//                }else{
//                    textView6.text="wrong"
//
//                }
//            }
            login()

        }



        buttonSignUp.setOnClickListener {
            val intent: Intent = Intent(this, Signup::class.java)
            startActivityForResult(intent, SIGNUP_REQUEST_CODE)
        }
    }

    fun login() {

        val queue = Volley.newRequestQueue(this)
        val url = "https://mfnodemcu.000webhostapp.com/android/login.php?username=" + editTextLogUsername.text.toString() + "&password=" + editTextLogPassword.text.toString()


// Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.

                if (response.getBoolean("valid") == false) {
                    Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_LONG).show()
                } else {
                    sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE)
                    var edit: SharedPreferences.Editor = sharedPreferences.edit()
                    edit.putString("user_id", response.getString("id"))
                    edit.apply()
                    val intent = getIntent()
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                }
            },
            Response.ErrorListener { error -> textView6.text = error.toString() })

// Add the request to the RequestQueue.
        queue.add(stringRequest)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == SIGNUP_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
            }

        }




        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val SIGNUP_REQUEST_CODE = 1

    }


}
