package com.plocki.allegrointern.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plocki.allegrointern.R
import com.plocki.allegrointern.model.Offer

class ViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val context: Context) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.offer, parent, false)), View.OnClickListener {

    private val offerImage : ImageView = itemView.findViewById(R.id.offerImage)
    private val offerName : TextView = itemView.findViewById(R.id.offerName)
    private val offerPrice : TextView = itemView.findViewById(R.id.offerPrize)

    override fun onClick(view: View?) {
//        val intent = Intent(context, Details::class.java)
//        intent.putExtra("pos", layoutPosition.toString())
//        context.startActivity(intent)
    }


    fun bind(offer: Offer){
        val priceString = "${offer.price!!.amount} ${offer.price!!.currency}"
        offerName.text = offer.name
        offerPrice.text = priceString

        Glide.with(context)
            .load(offer.thumbnailUrl)
            .into(offerImage)

    }

}