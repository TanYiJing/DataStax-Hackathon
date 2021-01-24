package com.example.give.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.give.HypermarketScreen
import com.example.give.R
import com.example.give.classes.Cart
import com.example.give.classes.Hypermarket
import com.example.give.classes.Product
import com.squareup.picasso.Picasso
import kotlin.math.round
import kotlin.math.roundToLong


class CartAdapter internal constructor(context: Context, listner: AdapterCallback) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private lateinit var sharedPreferences: SharedPreferences
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cartList: MutableList<Cart> = mutableListOf<Cart>()
    private val adapterCallback = listner
    val selectedCartList: MutableList<Cart> = mutableListOf<Cart>()
    var freeDelivery=false
    public val context1: Context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = inflater.inflate(R.layout.recycleview_cart, parent, false)

        return CartViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartRecord = cartList.get(position)
        holder.cartProductPrice.text = "RM" + cartRecord.prod_price.toString()
        holder.cartProductName.text = cartRecord.prod_name
        Picasso.get().load(cartRecord.prod_url).into(holder.carProductImageView);
        holder.cartTextQty.text = cartRecord.qty.toString()


        holder.cartProductNeg.setOnClickListener {
            if (cartRecord.qty > 1) {

                holder.cartTextQty.text = (cartRecord.qty - 1).toString()
                cartRecord.qty = cartRecord.qty - 1
                adapterCallback.myCartTotalFees(calculateFees().toString(),freeDelivery)
            }
        }

        holder.cartProductPos.setOnClickListener {
            if (cartRecord.qty < 50) {
                holder.cartTextQty.text = (cartRecord.qty + 1).toString()
                cartRecord.qty = cartRecord.qty + 1
                adapterCallback.myCartTotalFees(calculateFees().toString(),freeDelivery)

            }

        }

        holder.cartProductCheckBox.setOnClickListener {
            if (holder.cartProductCheckBox.isChecked) {
                selectedCartList.add(cartRecord)
                adapterCallback.myCartTotalFees(calculateFees().toString(),freeDelivery)

            } else {
                selectedCartList.remove(cartRecord)
                adapterCallback.myCartTotalFees(calculateFees().toString(),freeDelivery)
            }

        }
        holder.carProductDetele.setOnClickListener {


            sharedPreferences = context1.getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE)
            val queue = Volley.newRequestQueue(context1)
            val url =
                "https://mfnodemcu.000webhostapp.com/android/removecart.php?custid=" + sharedPreferences.getString(
                    "user_id",
                    ""
                ) + "&productid=" + cartRecord.prod_id

// Request a string response from the provided URL.
            val stringRequest = JsonArrayRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->

                },
                Response.ErrorListener { error -> })

// Add the request to the RequestQueue.
            queue.add(stringRequest)
            cartList.remove(cartRecord)
            selectedCartList.remove(cartRecord)
            notifyDataSetChanged()
            adapterCallback.myCartTotalFees(calculateFees().toString(),freeDelivery)
        }


    }


    override fun onViewRecycled(holder: CartViewHolder) {
        super.onViewRecycled(holder)
    }


     fun getSelectedProduct():MutableList<Cart>
    {

        return selectedCartList
    }

    fun calculateFees(): String {
        var totalfess: Double = 0.0
        if (selectedCartList.size != 0) {
            totalfess += 2
            for (i in 0..selectedCartList.size - 1) {
                totalfess += selectedCartList.get(i).prod_price * selectedCartList.get(i).qty

            }
        }
        freeDelivery=false

        if (totalfess > 32){
            totalfess -= 2
            freeDelivery=true
        }
        return String.format("RM %.2f",totalfess)
    }

    internal fun setCartList(cartList: MutableList<Cart>) {
        this.cartList = cartList
        notifyDataSetChanged()

    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartProductCheckBox: CheckBox = itemView.findViewById(R.id.cartproductCheckbox)
        val cartProductPrice: TextView = itemView.findViewById(R.id.cartProductPrice)
        val cartProductName: TextView = itemView.findViewById(R.id.cartProductName)
        val carProductImageView: ImageView = itemView.findViewById(R.id.cartProductImage)
        val cartProductPos: ImageView = itemView.findViewById(R.id.posCartQty)
        val cartProductNeg: ImageView = itemView.findViewById(R.id.negCartQty)
        val cartTextQty: TextView = itemView.findViewById((R.id.textCartQty))


        val carProductDetele: ImageView = itemView.findViewById(R.id.cartProductDelete)


    }
}