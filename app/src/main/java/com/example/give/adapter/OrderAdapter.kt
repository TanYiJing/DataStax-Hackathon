package com.example.give.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.give.HypermarketScreen
import com.example.give.MyOrderList
import com.example.give.R
import com.example.give.classes.Hypermarket
import com.example.give.classes.Order
import com.squareup.picasso.Picasso

class OrderAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<OrderAdapter.OrderAdapterViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var orderList = emptyList<Order>()
    public val context1: Context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapterViewHolder {
        val itemView = inflater.inflate(R.layout.recycleview_myorder, parent, false)
        return OrderAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderAdapterViewHolder, position: Int) {
        val orderRecord = orderList.get(position)
        holder.orderrecycleDate.text = orderRecord.orderDate
        holder.orderrecycleID.text = orderRecord.id.toString()
        holder.orderrecycleTotal.text = "RM " + orderRecord.totalPrice.toString()
        holder.orderrecycleImageView.setOnClickListener {
            val intent = Intent(context1, MyOrderList::class.java)
            intent.putExtra("purchaseid",orderRecord.id)
            intent.putExtra("address",orderRecord.address)
            intent.putExtra("date",orderRecord.orderDate)
            intent.putExtra("total",orderRecord.totalPrice)

            context1.startActivity(intent)
        }

    }

    internal fun setOrderList(orderList: List<Order>) {
        this.orderList = orderList
        notifyDataSetChanged()

    }

    inner class OrderAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val orderrecycleDate: TextView = itemView.findViewById(R.id.orderDate)
        val orderrecycleID: TextView = itemView.findViewById(R.id.orderID)
        val orderrecycleTotal: TextView = itemView.findViewById(R.id.orderTotalPrice)
        val orderrecycleImageView: ImageView = itemView.findViewById(R.id.myOrderView)


    }
}