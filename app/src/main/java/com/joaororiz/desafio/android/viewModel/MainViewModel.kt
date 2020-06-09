package com.joaororiz.desafio.android.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.joaororiz.desafio.android.data.entities.Character
import com.joaororiz.desafio.android.datasource.Listing
import com.joaororiz.desafio.android.service.CharactereRepository
import com.joaororiz.desafio.android.ui.base.BaseViewModel

class MainViewModel(charactereRepository: CharactereRepository) : BaseViewModel() {

    private val listing = MutableLiveData<Listing<Character>>()
    val list = switchMap(listing) { it.pagedList }
    val networkState = switchMap(listing) { it.networkState }

    init {
        launch {
            listing.value = charactereRepository.listAll()
        }
    }

    fun refresh() {
        listing.value?.refresh?.invoke()
    }

    fun retry() {
        listing.value?.retry?.invoke()
    }
}