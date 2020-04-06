package com.plocki.allegrointern.network


import com.plocki.allegrointern.model.Offer
import com.plocki.allegrointern.model.Offers
import retrofit2.Call
import retrofit2.http.GET

interface OfferService {
    @GET("allegro/offers")
    fun getOffers(): Call<Offers>
}