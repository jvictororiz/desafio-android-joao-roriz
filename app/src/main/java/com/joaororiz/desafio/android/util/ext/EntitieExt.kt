package com.joaororiz.desafio.android.util.ext

import com.joaororiz.desafio.android.data.entities.Thumbnail
import java.math.BigInteger
import java.security.MessageDigest
import java.text.NumberFormat
import java.util.*

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun Thumbnail.getLargeUrl(): String {
    return "$path/portrait_xlarge.$extension"
}

fun Thumbnail.getFantasticUrl(): String {
    return "$path/portrait_fantastic.$extension"
}

fun Float.toMoney(): String {
    val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "Br"))
    return try {
        return numberFormat.format(this)
    } catch (ex: Exception) {
        ex.printStackTrace()
        ""
    }
}