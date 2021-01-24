package com.example.give

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.give.adapter.CartAdapter
import com.example.give.adapter.OrderAdapter
import com.example.give.classes.Cart
import com.example.give.classes.Order
import kotlinx.android.synthetic.main.activity_my_cart.*

class MyOrder : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var adapter: OrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)

        sharedPreferences=getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE)

        val queue = Volley.newRequestQueue(this)
        val url = "https://mfnodemcu.000webhostapp.com/android/myorder.php?custid=" + sharedPreferences.getString("user_id", "")
        val orderList: MutableList<Order> = mutableListOf<Order>()

// Request a string response from the provided URL.
        val stringRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                for (i in 0..response.length() - 1) {
                    var jsonObject = response.getJSONObject(i)
                    orderList.add(
                        Order(
                            jsonObject.getInt("id"),
                            jsonObject.getString("totalPrice").toDouble(),
                            jsonObject.getString("orderDate"),
                            jsonObject.getString("address")

                        )
                    )
                }


                val recyclerView = findViewById<RecyclerView>(R.id.orderRVscreen)
                this.adapter = OrderAdapter(this)
                recyclerView.adapter = this.adapter
                recyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

                adapter.setOrderList(orderList)

            },
            Response.ErrorListener { error ->
            })




        queue.add(stringRequest)





    }
}
