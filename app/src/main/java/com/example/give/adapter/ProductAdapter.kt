package com.example.give.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.give.HypermarketScreen
import com.example.give.ProductDetail
import com.example.give.R
import com.example.give.classes.Hypermarket
import com.example.give.classes.Product
import com.squareup.picasso.Picasso

class ProductAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHodler>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var productList = emptyList<Product>()
    public val context1: Context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHodler {
        val itemView = inflater.inflate(R.layout.recycleview_product, parent, false)
        return ProductViewHodler(itemView)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHodler, position: Int) {
        val productRecord = productList.get(position)
        Picasso.get().load(productRecord.imageUrl).into(holder.productrecycleViewImage);
        holder.productrecycleViewName.text = productRecord.name
        holder.productrecycleViewID.text = productRecord.id.toString()
        holder.productrecycleViewexpDate.text = productRecord.expdate
        holder.productrecycleViewOriPrice.text = "RM " +productRecord.oriPrice.toString()
        holder.productrecycleViewDiscPrice.text = "RM "+productRecord.discPrice.toString()
        holder.productrecycleViewPercentage.text = productRecord.percentage.toString() +"%"

        holder.productrecycleView.setOnClickListener {
            val intent = Intent(context1, ProductDetail::class.java)
            intent.putExtra("ProductID", productRecord.id.toString())
            intent.putExtra("ProductName", productRecord.name)
            intent.putExtra("ProductDesc", productRecord.desc)
            intent.putExtra("ProductimgUrl", productRecord.imageUrl)
            intent.putExtra("ProductExpDate", productRecord.expdate)
            intent.putExtra("ProductOriprice", productRecord.oriPrice.toString())
            intent.putExtra("ProductDiscPrice", productRecord.discPrice.toString())
            intent.putExtra("ProductPercentage", productRecord.percentage.toString())
            intent.putExtra("ProductStock", productRecord.stock.toString())
            intent.putExtra("ProductQtysold", productRecord.qtySold)

            context1.startActivity(intent)
        }
    }

    internal fun setProductList(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()

    }




    inner class ProductViewHodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productrecycleView: androidx.cardview.widget.CardView  = itemView.findViewById(R.id.productRecycleView)

        val productrecycleViewID: TextView = itemView.findViewById(R.id.product_id)
        val productrecycleViewImage: ImageView = itemView.findViewById(R.id.product_imgUrl)
        val productrecycleViewName: TextView = itemView.findViewById(R.id.product_name)
        val productrecycleViewexpDate: TextView = itemView.findViewById(R.id.product_expDate)
        val productrecycleViewOriPrice: TextView = itemView.findViewById(R.id.product_oriPrice)
        val productrecycleViewDiscPrice: TextView = itemView.findViewById(R.id.product_discPrice)
        val productrecycleViewPercentage: TextView = itemView.findViewById(R.id.discount_percent)


    }
}