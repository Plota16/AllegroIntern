package com.plocki.allegrointern.recycler

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plocki.allegrointern.DetailActivity
import com.plocki.allegrointern.model.Offer
import com.plocki.allegrointern.model.ApiResponse

class ListAdapter(private val offerList: ArrayList<Offer>, private val context: Context)
    : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val offer: Offer = offerList[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("url",offerList[position].thumbnailUrl)
            intent.putExtra("name",offerList[position].name)
            intent.putExtra("description",offerList[position].description)
            intent.putExtra("amount",offerList[position].price!!.amount)
            intent.putExtra("currency",offerList[position].price!!.currency)
            context.startActivity(intent)
        }
        holder.bind(offer)
    }

    override fun getItemCount(): Int {
        return offerList.size
    }
}