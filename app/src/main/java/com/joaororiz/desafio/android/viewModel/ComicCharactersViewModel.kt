package com.joaororiz.desafio.android.viewModel

import androidx.lifecycle.MutableLiveData
import com.joaororiz.desafio.android.R
import com.joaororiz.desafio.android.data.entities.*
import com.joaororiz.desafio.android.service.CharactereRepository
import com.joaororiz.desafio.android.ui.base.BaseViewModel
import com.joaororiz.desafio.android.util.ext.toMoney
import com.joaororiz.desafio.android.util.resources.ResourceProvider

class ComicCharactersViewModel(val resources: ResourceProvider) : BaseViewModel() {
    val error = MutableLiveData<String>()
    val listObservable = MutableLiveData<List<String>>()

    fun selectComic(comic: Comic) {

        if (comic.prices.isNullOrEmpty()) {
            error.value = resources.getString(R.string.not_prices)
        } else {
            val list = comic.prices.map {
                val type = when (it.type) {
                    TypePrice.DIGITAL_PURCHASE_PRICE -> {
                        resources.getString(R.string.digital_purchase)
                    }
                    TypePrice.PRINT_PRICE -> {
                        resources.getString(R.string.print_price)
                    }
                }
                resources.getString(R.string.text_price, type, it.price.toMoney())
            }
            listObservable.value = list
        }
    }


}
