package com.joaororiz.desafio.android.util.resources

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(private val mContext: Context) {
    fun getString(resId: Int): String {
        return mContext.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
        return mContext.getString(resId, *formatArgs)
    }

}