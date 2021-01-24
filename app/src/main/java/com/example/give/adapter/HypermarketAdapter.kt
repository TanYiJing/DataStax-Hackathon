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
import com.example.give.R
import com.example.give.classes.Hypermarket
import com.squareup.picasso.Picasso

class HypermarketAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<HypermarketAdapter.HypermarketViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var hypermarketList = emptyList<Hypermarket>()
    public val context1: Context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HypermarketViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_hypermarket, parent, false)
        return HypermarketViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return hypermarketList.size
    }

    override fun onBindViewHolder(holder: HypermarketViewHolder, position: Int) {
        val hypermarketRecord = hypermarketList.get(position)
        Picasso.get().load(hypermarketRecord.imageUrl.toString())
            .into(holder.hyperMarketrecycleImage);
        holder.hyperMarketrecycletName.text = hypermarketRecord.name.toString()
        holder.hyperMarketrecycleID.text = hypermarketRecord.id.toString()
        holder.hyperMarketrecycleView.setOnClickListener {
            val intent = Intent(context1, HypermarketScreen::class.java)
            intent.putExtra("HypermarketID", holder.hyperMarketrecycleID.text.toString())
            intent.putExtra("HypermarketName", holder.hyperMarketrecycletName.text.toString())

            context1.startActivity(intent)


        }
    }

    internal fun setHypermarketList(hypermarketList: List<Hypermarket>) {
        this.hypermarketList = hypermarketList
        notifyDataSetChanged()

    }

    inner class HypermarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hyperMarketrecycleView: RelativeLayout =
            itemView.findViewById(R.id.HyperMarketrecycleView)
        val hyperMarketrecycleImage: ImageView = itemView.findViewById(R.id.HyperMarketImage)
        val hyperMarketrecycletName: TextView = itemView.findViewById(R.id.HyperMarketName)
        val hyperMarketrecycleID: TextView = itemView.findViewById(R.id.HyperMarketID)


    }
}