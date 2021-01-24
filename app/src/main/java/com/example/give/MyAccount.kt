package com.example.give

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_my_account.*
import kotlinx.android.synthetic.main.activity_my_account.editEmail
import kotlinx.android.synthetic.main.activity_signup.*
import java.text.SimpleDateFormat
import java.util.*
import android.text.InputType



class MyAccount : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var addressInput: EditText
lateinit var oldpassworld:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        myaccounteditEmail.isEnabled=false
        myaccounteditPass.isEnabled=false
        myaccounteditName.isEnabled=false
        myaccounteditPass.setText("asdasdasd")


        sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE)
        val queue = Volley.newRequestQueue(this)
        val url = "https://mfnodemcu.000webhostapp.com/android/myaccount.php?custid=" +sharedPreferences.getString("user_id","")

        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
               myaccounteditEmail.setText( response.getString("email"))
                myaccounteditName.setText(response.getString("name"))
        oldpassworld =response.getString("password")
            },
            Response.ErrorListener { error -> textView5.text = error.toString() })

// Add the request to the RequestQueue.
        queue.add(stringRequest)




        editName.setOnClickListener {
            shownameDialog() }
        editEmail.setOnClickListener {
            shownEmailDialog()
        }
        editPass.setOnClickListener {
             showpassDialog()
        }
    }


    fun shownameDialog() {
        val context = this
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Edit Profile")
        builder.setMessage("Please enter your new name ")
        addressInput = EditText(this)
        builder.setView(addressInput)
        builder.setPositiveButton("Submit", DialogInterface.OnClickListener { dialog, which ->
            val queue = Volley.newRequestQueue(this)
            val url = "https://mfnodemcu.000webhostapp.com/android/editname.php?custId="+ sharedPreferences.getString("user_id","")+"&name="+addressInput.text.toString()
            val stringRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->

                    myaccounteditName.setText(addressInput.text.toString() )
                },
                Response.ErrorListener { error ->  })

// Add the request to the RequestQueue.
            queue.add(stringRequest)
        })
        builder.setNegativeButton("Cancel",DialogInterface.OnClickListener{ dialog, which ->
        dialog.dismiss()
        })
        builder.create().show()
    }
    fun showpassDialog() {
        val context = this
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Edit Profile")
        builder.setMessage("Please enter your old pass word ")
        addressInput.inputType= InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(addressInput)
        builder.setPositiveButton("Submit", DialogInterface.OnClickListener { dialog, which ->

            if(addressInput.text.toString().equals(oldpassworld))
             showpassDialog2()
            else{
                dialog.dismiss()
                Toast.makeText(this,"invalid password",Toast.LENGTH_SHORT).show()
            }
        })

        builder.setNegativeButton("Cancel",DialogInterface.OnClickListener{ dialog, which ->
            dialog.dismiss()
        })
        builder.create().show()
    }
    fun showpassDialog2() {
        val context = this
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Edit Profile")
        builder.setMessage("Please enter your new pass word ")
        addressInput = EditText(this)
        addressInput.inputType= InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(addressInput)
        builder.setPositiveButton("Submit", DialogInterface.OnClickListener { dialog, which ->
            val queue = Volley.newRequestQueue(this)
            val url = "https://mfnodemcu.000webhostapp.com/android/editpassword.php?custId="+ sharedPreferences.getString("user_id","")+"&password="+addressInput.text.toString()
            val stringRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->

                    myaccounteditEmail.setText(addressInput.text.toString() )
                },
                Response.ErrorListener { error ->  })

// Add the request to the RequestQueue.
            queue.add(stringRequest)
        })
        builder.setNegativeButton("Cancel",DialogInterface.OnClickListener{ dialog, which ->
            dialog.dismiss()
        })
        builder.create().show()
    }




    fun shownEmailDialog() {
        val context = this
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Edit Profile")
        builder.setMessage("Please enter your new email ")
        addressInput = EditText(this)
        builder.setView(addressInput)
        builder.setPositiveButton("Submit", DialogInterface.OnClickListener { dialog, which ->

            val queue = Volley.newRequestQueue(this)
            val url = "https://mfnodemcu.000webhostapp.com/android/editemail.php?custId="+ sharedPreferences.getString("user_id","")+"&email="+addressInput.text.toString()
            val stringRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->

                    myaccounteditEmail.setText(addressInput.text.toString() )
                },
                Response.ErrorListener { error ->  })

// Add the request to the RequestQueue.
            queue.add(stringRequest)

        })
        builder.setNegativeButton("Cancel",DialogInterface.OnClickListener{ dialog, which ->
            dialog.dismiss()
        })
        builder.create().show()
    }


}
