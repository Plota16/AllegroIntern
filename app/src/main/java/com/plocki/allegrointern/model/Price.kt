package com.plocki.allegrointern.model

import com.google.gson.annotations.SerializedName

class Price {
    @SerializedName("amount")
    var amount: Double? = null
    @SerializedName("currency")
    var currency: String? = null

}