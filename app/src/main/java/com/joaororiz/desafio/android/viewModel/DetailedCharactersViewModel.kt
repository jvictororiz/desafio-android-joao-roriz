package com.joaororiz.desafio.android.viewModel

import androidx.lifecycle.MutableLiveData
import com.joaororiz.desafio.android.data.entities.Character
import com.joaororiz.desafio.android.data.entities.Comic
import com.joaororiz.desafio.android.data.entities.DataWrapper
import com.joaororiz.desafio.android.data.entities.GlobalResponse
import com.joaororiz.desafio.android.service.CharactereRepository
import com.joaororiz.desafio.android.ui.base.BaseViewModel

class DetailedCharactersViewModel(private val charactereRepository: CharactereRepository) : BaseViewModel() {

    val load = MutableLiveData<Boolean>()

    val successTopComic = MutableLiveData<Comic>()
    val error = MutableLiveData<String>()

    fun findComics(character: Character) = launch {
        load.value = true
        val result = charactereRepository.findComicsByCharactere(character.id)
        if (result.isSuccessful()) {
            treatmentSuccess(result)
        } else {
            error.value = result.errorMessage()
        }
        load.value = false
    }

    private fun treatmentSuccess(result: DataWrapper<GlobalResponse<Comic>?>) {
        if ((result.data?.results).isNullOrEmpty()) {
            error.value = "Não possui nenhuma comic"
        } else {
            val maxPrice = result.data?.results?.maxBy {
                it.prices.maxBy { price ->
                    price.price
                }?.price?.toInt() ?: 0
            }
            successTopComic.value = maxPrice
        }
    }


}
