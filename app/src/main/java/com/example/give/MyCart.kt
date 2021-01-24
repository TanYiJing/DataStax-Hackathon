package com.example.give

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.give.adapter.AdapterCallback
import com.example.give.adapter.CartAdapter
import com.example.give.adapter.ProductAdapter
import com.example.give.classes.Cart
import com.example.give.classes.Product
import kotlinx.android.synthetic.main.activity_my_cart.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_product_screen.*
import java.util.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.text.SimpleDateFormat
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.view.WindowManager
import kotlinx.coroutines.delay
import java.util.concurrent.LinkedBlockingQueue


class MyCart : AppCompatActivity(), AdapterCallback {
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var adapter: CartAdapter
    lateinit var addressInput: EditText
    private val mHandler = Handler()

    var dialogsToShow: LinkedBlockingQueue<Dialog> = LinkedBlockingQueue()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE)
        val queue = Volley.newRequestQueue(this)
        val url =
            "https://mfnodemcu.000webhostapp.com/android/mycart.php?custid=" + sharedPreferences.getString(
                "user_id",
                ""
            )

        val cartlist: MutableList<Cart> = mutableListOf<Cart>()
// Request a string response from the provided URL.
        val stringRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
                for (i in 0..response.length() - 1) {
                    var jsonObject = response.getJSONObject(i)
                    cartlist.add(
                        Cart(
                            jsonObject.getInt("id"),
                            jsonObject.getString("name"),
                            jsonObject.getString("price").toDouble(),
                            jsonObject.getString("url").replace("\\/", "/"),
                            jsonObject.getString("qty").toInt()

                        )
                    )
                }

                val recyclerView = findViewById<RecyclerView>(R.id.cartRVscreen)
                this.adapter = CartAdapter(this, this)
                recyclerView.adapter = this.adapter
                recyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

                adapter.setCartList(cartlist)


            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

        CheckOutbutton.setOnClickListener {

            showCreateCategoryDialog()

        }

    }

    private fun addPurchaselist(selectedCart: MutableList<Cart>) {
        val queue = Volley.newRequestQueue(this)

        for (i in selectedCart) {



            mHandler.postDelayed(kotlinx.coroutines.Runnable { val url =
                "https://mfnodemcu.000webhostapp.com/android/addpurchaselist.php?productid=" + i.prod_id + "&custid=" +sharedPreferences.getString("user_id", "") + "&qty=" +i.qty
                val stringRequest = JsonArrayRequest(
                    Request.Method.GET, url, null,
                    Response.Listener { response ->
                    },
                    Response.ErrorListener { error ->
                    })
                queue.add(stringRequest) },500)





        }

        Toast.makeText(this,"Paid",Toast.LENGTH_SHORT).show()
    }

    private fun creeatePurchase(date: String) {

        val queue = Volley.newRequestQueue(this)
        val url =
            "https://mfnodemcu.000webhostapp.com/android/createPurchase.php?total=" + mycartTotal.text.toString().substring(3) + "&purchasedate=" + date + "&address=" + addressInput.text.toString() + "&cust_id=" + sharedPreferences.getString(
                "user_id",
                ""
            )

// Request a string response from the provided URL.
        val stringRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
            },
            Response.ErrorListener { error ->
            })
        queue.add(stringRequest)
    }




    override fun myCartTotalFees(fees: String, freeDelivery: Boolean) {
        if (freeDelivery) {
            mycartShipping.text = "free shipping"
            CheckOutbutton.isEnabled = true
        } else if (freeDelivery == false && fees.substring(3).toDouble() > 0) {

            mycartShipping.text = "RM 2.00"
            CheckOutbutton.isEnabled = true
        } else {
            mycartShipping.text = "RM 0.00"
            CheckOutbutton.isEnabled = false
        }

        mycartTotal.text = fees
    }

    fun showCreateCategoryDialog() {
        val context = this
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Shipping Address")
        builder.setMessage("Please enter your address")
        addressInput = EditText(this)
        builder.setView(addressInput)
        builder.setPositiveButton("Submit", DialogInterface.OnClickListener { dialog, which ->
            var selectedCart = adapter.getSelectedProduct()
            var date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
            val date2 = formatter.format(date)
            creeatePurchase(date2)
            addPurchaselist(selectedCart)
        })


        builder.create().show()

    }


}
