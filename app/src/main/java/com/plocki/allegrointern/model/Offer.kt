package com.plocki.allegrointern.model

import com.google.gson.annotations.SerializedName

class Offer {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String? = null
    @SerializedName("price")
    var price: Price? = null
    @SerializedName("description")
    var description: String? = null
}