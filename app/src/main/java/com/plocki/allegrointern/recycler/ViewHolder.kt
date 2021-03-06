package com.plocki.allegrointern.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.plocki.allegrointern.DetailActivity
import com.plocki.allegrointern.R
import com.plocki.allegrointern.model.Offer


class ViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val context: Context) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.offer, parent, false)){

    private val offerImage : ImageView = itemView.findViewById(R.id.offerImage)
    val offerName : TextView = itemView.findViewById(R.id.offerName)
    val offerPrice : TextView = itemView.findViewById(R.id.offerPrize)



    fun bind(offer: Offer){
        val priceString = "${DetailActivity.toPolishFormat(offer.price!!.amount!!)} ${offer.price!!.currency}"
        offerName.text = offer.name
        offerPrice.text = priceString

        val options = RequestOptions()
            .fitCenter()

        Glide.with(context)
            .load(offer.thumbnailUrl)
            .apply(options)
            .into(offerImage)

    }

}