package com.example.give.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.give.R
import com.example.give.classes.Order
import com.example.give.classes.OrderList
import com.squareup.picasso.Picasso


class OrderListAdapter internal constructor(context: Context) : RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var orderList = emptyList<OrderList>()
    public val context1: Context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val itemView = inflater.inflate(R.layout.recycview_orderlist, parent, false)
        return OrderListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val orderRecord = orderList.get(position)
        holder.orderListrecycleID.text= orderRecord.id.toString()
        holder.orderListrecycleName.text= orderRecord.name
        holder.orderListrecyclePrice.text= orderRecord.price
        holder.orderListrecycleqty.text= orderRecord.qty.toString()
        Picasso.get().load(orderRecord.url).into(holder.orderListrecycleimage);
        holder.orderListrecycletotal.text= "RM "+(orderRecord.qty.toDouble()*orderRecord.price.toDouble()).toString()



    }

    internal fun setOrderList(orderList: List<OrderList>) {
        this.orderList = orderList
        notifyDataSetChanged()

    }

    inner class OrderListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val orderListrecycleID: TextView = itemView.findViewById(R.id.orderlistID)
        val orderListrecycleimage: ImageView = itemView.findViewById(R.id.orderlistImage)
        val orderListrecycleName: TextView = itemView.findViewById(R.id.orderlistName)
        val orderListrecyclePrice: TextView = itemView.findViewById(R.id.ordelistPrice)
        val orderListrecycleqty: TextView = itemView.findViewById(R.id.ordelistqty)
        val orderListrecycletotal: TextView = itemView.findViewById(R.id.ordelistsubtotal)

    }
}