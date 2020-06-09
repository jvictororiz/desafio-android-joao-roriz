package com.joaororiz.desafio.android.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GlobalResponse<T : Parcelable>(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<T>
) : Parcelable

data class DataWrapper<T>(
    val data: T? = null,
    val attributionText: String? = null,
    val code: Int? = null
) {
    fun isSuccessful() = code == 200

    fun errorMessage() = attributionText

    companion object {
        fun <T> error(msg: String?, code: Int? = null) =
            DataWrapper<T>(
                attributionText = msg,
                code = code
            )

        fun <T> success(data: T, code: Int? = null) =
            DataWrapper(
                data = data,
                code = code
            )
    }

}