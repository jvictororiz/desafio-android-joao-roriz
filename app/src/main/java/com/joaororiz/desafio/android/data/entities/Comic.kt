package com.joaororiz.desafio.android.data.entities

import android.os.Parcelable
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.joaororiz.desafio.android.data.deserializer.TypePriceDeserializer
import kotlinx.android.parcel.Parcelize

enum class TypePrice(val code: String) {
    PRINT_PRICE("printPrice"),
    DIGITAL_PURCHASE_PRICE("digitalPurchasePrice")
}

@Parcelize
data class ComicPrice(
    @JsonAdapter(TypePriceDeserializer::class)
    val type: TypePrice,
    val price: Float
) : Parcelable

@Parcelize
data class Comic(
    val title: String,
    val description: String?="",
    val thumbnail: Thumbnail,
    val prices: List<ComicPrice>
) : Parcelable