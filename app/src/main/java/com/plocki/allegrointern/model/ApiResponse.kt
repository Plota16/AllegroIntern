package com.plocki.allegrointern.model

import com.google.gson.annotations.SerializedName

class ApiResponse {
    @SerializedName("offers")
    var offers: ArrayList<Offer> = ArrayList<Offer>()

}