package com.example.give

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.give.adapter.OrderListAdapter
import com.example.give.classes.OrderList
import kotlinx.android.synthetic.main.activity_order_list.*
import java.math.RoundingMode
import java.math.BigDecimal

class MyOrderList : AppCompatActivity() {
    lateinit var adapter: OrderListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        val intent = getIntent()

        val queue = Volley.newRequestQueue(this)
        val url = "https://mfnodemcu.000webhostapp.com/android/viewpurchaselist.php?purchaseid=" + intent.getIntExtra("purchaseid",0).toString()
        val myOrderList: MutableList<OrderList> = mutableListOf<OrderList>()

// Request a string response from the provided URL.
        val stringRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                for (i in 0..response.length() - 1) {
                    var jsonObject = response.getJSONObject(i)
                    myOrderList.add(
                        OrderList(
                            jsonObject.getInt("id"),
                            jsonObject.getString("url"),
                            jsonObject.getString("name"),
                            jsonObject.getString("price"),
                            jsonObject.getInt("qty")

                        )
                    )
                }


                val recyclerView = findViewById<RecyclerView>(R.id.orderListRVscreen)
                this.adapter = OrderListAdapter(this)
                recyclerView.adapter = this.adapter
                recyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

                adapter.setOrderList(myOrderList)

            },
            Response.ErrorListener { error ->
            })

        orderlistAddress.text= intent.getStringExtra("address")
        orderlistDate.text= "Order placed on "+intent.getStringExtra("date")

        val a =intent.getDoubleExtra("total",0.0)
        BigDecimal(a).setScale(2, RoundingMode.HALF_EVEN)
        orderlistTotal.text= "Total RM"+ a
        queue.add(stringRequest)


    }
}
