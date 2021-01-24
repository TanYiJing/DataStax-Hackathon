package com.example.give

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.give.database.user.User
import com.example.give.database.user.UserViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val intent= getIntent()
        buttonConfirm.setOnClickListener {
            //            userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
//
//            if(editTextLogPassword.text!=null && editTextLogUsername.text!=null){
//                userViewModel.insertUser(
//                    User(
//                        0,
//                        editTextUsername.text.toString(),
//                        editTextPassword.text.toString()
//                    )
//                )
//                finish()
//
//            }
            if (editEmail.text.toString() != "" && editTextName.text.toString() != "" && editTextUsername.text.toString() != "" && editTextPassword.text.toString() != "" && editTextCofirmPassword.text.toString() != "") {
                if (verifyPassword()) {
                    register()
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "password and confirm password not match ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(this, "Please Fill All The Details", Toast.LENGTH_LONG).show()


            }
        }
        buttonCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }


    }

    fun verifyPassword(): Boolean {
        if (editTextPassword.text.toString().equals(editTextCofirmPassword.text.toString()))
            return true

        return false

    }

    fun register() {

        val queue = Volley.newRequestQueue(this)
        val url =
            "https://mfnodemcu.000webhostapp.com/android/signup.php?username=" + editTextUsername.text.toString() + "&password=" + editTextPassword.text.toString() +"&name="+editTextName.text.toString()+"&email="+editEmail.text.toString()


// Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
                if(!response.getBoolean("valid")){

                    Toast.makeText(this,"fail to sign up, username already exist",Toast.LENGTH_SHORT).show()


                }else{
                    Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show()


                }
            },
            Response.ErrorListener { error -> textView5.text = error.toString() })

// Add the request to the RequestQueue.
        queue.add(stringRequest)


    }
}
