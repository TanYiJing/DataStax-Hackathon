package com.example.give

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import android.text.method.ScrollingMovementMethod
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.give.adapter.HypermarketAdapter
import com.example.give.classes.Hypermarket
import kotlinx.android.synthetic.main.activity_home.*


class ProductDetail : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)


        val intent = getIntent()
        Picasso.get().load(intent.getStringExtra("ProductimgUrl")).into(ProductDetailImage);
        ProductDetailDiscPrice.text="RM "+intent.getStringExtra("ProductDiscPrice")
        ProductDetailOriPrice.text="RM "+intent.getStringExtra("ProductOriprice")
        productDetailPercentage.text="-"+intent.getStringExtra("ProductPercentage") + " %"
        ProductDetailDesc.text=intent.getStringExtra("ProductDesc")
        ProductDetailExpdate.text=intent.getStringExtra("ProductExpDate")
        ProductDetailStock.text=intent.getStringExtra("ProductStock")
        ProductDetailDesc.setMovementMethod(ScrollingMovementMethod())

        if(intent.getStringExtra("ProductStock").toString().toInt()<1){

            btnAddToCart.isClickable=false
            btnAddToCart.setText("Sold Out")
        btnAddToCart.isEnabled=false
        }

        btnAddToCart.setOnClickListener {
             if( productDetailQtyPicker.text.toString().toInt() > intent.getStringExtra("ProductStock").toInt()||productDetailQtyPicker.text.toString().toInt() <=0 )
             {
                 Toast.makeText(this,"Select a quantity between 0 to " +intent.getStringExtra("ProductStock"),Toast.LENGTH_LONG).show()

             }
              else{

                 sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE)
                 val custId = sharedPreferences.getString("user_id","")
                 val productId =intent.getStringExtra("ProductID")


                 val queue = Volley.newRequestQueue(this)
                 val url = "https://mfnodemcu.000webhostapp.com/android/addtocart.php?custId="+custId+"&productId="+productId+"&qty="+productDetailQtyPicker.text.toString()

// Request a string response from the provided URL.
                 val stringRequest = StringRequest(
                     Request.Method.GET, url,
                     Response.Listener<String> { response ->
                    Toast.makeText(this,"item succefully added to cart",Toast.LENGTH_SHORT).show()
                     },
                     Response.ErrorListener {  })
                 queue.add(stringRequest)
             }

          }

        btnProductDetailCancel.setOnClickListener {


            finish()
        }


    }



}

