package com.example.give

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.graphics.Bitmap
import android.R.attr.data
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val LOGIN_REQUEST_CODE = 1
        const val HOME_REQUEST_CODE = 2

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE)

        if (!sharedPreferences.getString("user_id", "").equals("")) {
            val intent = Intent(this, Home::class.java)
            startActivityForResult(intent, HOME_REQUEST_CODE)

        } else {

            val intent = Intent(this, Login::class.java)
            startActivityForResult(intent, LOGIN_REQUEST_CODE)

        }



//          val intent = Intent(this , Home::class.java)
//         startActivityForResult(intent , 1)
        //is for store image
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, 1)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

//        if(requestCode==LOGIN_REQUEST_CODE)
//        if (resultCode == Activity.RESULT_OK) {
//
//            val imageUri = data?.data
//            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
//
//            val stream :ByteArrayOutputStream = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
//            val byteArray:ByteArray = stream.toByteArray()
//            bitmap.recycle()
//            val bitmap_tmp=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
//            imageView3.setImageBitmap(bitmap_tmp)
//            imageView3.layoutParams.width=350;
//            imageView3.layoutParams.height=350;
//            imageView3.requestLayout()
//
//
//        }

        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val intent = Intent(this, Home::class.java)
                startActivityForResult(intent, HOME_REQUEST_CODE)
            }else{
                finish()
            }
        } else if (requestCode == HOME_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                if (data?.getBooleanExtra("logout", false)!!) {
                    val intent = Intent(this, Login::class.java)
                    startActivityForResult(intent, LOGIN_REQUEST_CODE)
                }
            }else if (resultCode == Activity.RESULT_CANCELED){
                finish()

            }
        }


        super.onActivityResult(requestCode, resultCode, data)

    }


}
