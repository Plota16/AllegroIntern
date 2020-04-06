package com.plocki.allegrointern.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plocki.allegrointern.model.Offer
import com.plocki.allegrointern.model.ApiResponse

class ListAdapter(private val apiResponse: ApiResponse, private val context: Context)
    : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val offer: Offer = apiResponse.offers[position]
        holder.bind(offer)
    }

    override fun getItemCount(): Int {
        return apiResponse.offers.size
    }
}