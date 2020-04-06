package com.plocki.allegrointern.services


import com.plocki.allegrointern.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface OfferService {
    @GET("allegro/offers")
    fun getOffers(): Call<ApiResponse>
}