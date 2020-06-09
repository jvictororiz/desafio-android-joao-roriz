package com.joaororiz.desafio.android.util.ext

import android.app.Activity
import android.content.Intent
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joaororiz.desafio.android.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


fun ImageView.setImageFromUrl(
    url: String?, @DrawableRes idImage: Int? = null,
    callbackSucess: (() -> Unit)? = null,
    callbackError: (() -> Unit)? = null,
    callbackFinish: (() -> Unit)? = null
) {
    Picasso.get()
        .load(url)
        .centerCrop()
        .apply { idImage?.let { placeholder(idImage).error(it) } }
        .fit()
        .into(this, object : Callback {
            override fun onSuccess() {
                callbackSucess?.invoke()
                callbackFinish?.invoke()
            }

            override fun onError(e: Exception?) {
                callbackError?.invoke()
                callbackFinish?.invoke()

            }
        })
}

fun ImageView.setImageFromUrl(
    picasso: Picasso,
    url: String?,
    @DrawableRes idImage: Int? = null,
    callbackSucess: (() -> Unit)? = null,
    callbackError: (() -> Unit)? = null,
    callbackFinish: (() -> Unit)? = null
) {
    picasso.load(url)
        .centerCrop()
        .fit()
        .apply { idImage?.let { placeholder(idImage).error(it) } }
        .fit()
        .into(this, object : Callback {
            override fun onSuccess() {
                callbackSucess?.invoke()
                callbackFinish?.invoke()
            }

            override fun onError(e: Exception?) {
                callbackError?.invoke()
                callbackFinish?.invoke()

            }
        })
}


fun RecyclerView.listenerEnd(onEnd: (Boolean) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val rowsArrayList = (adapter?.itemCount) ?: 0 - 1
            if (rowsArrayList < 1) return
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            onEnd(linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == rowsArrayList)
        }
    })
}

fun Activity.startActivityAnim(intent: Intent) {
    startActivity(intent)
    overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
}

fun Activity.startActivityAnim(intent: Intent, activityOptionsCompat: ActivityOptionsCompat) {
    startActivity(intent, activityOptionsCompat.toBundle())
    overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
}

fun Activity.hideWithAnim() {
    overridePendingTransition(R.anim.activity_out_back, R.anim.activity_in_back)
}