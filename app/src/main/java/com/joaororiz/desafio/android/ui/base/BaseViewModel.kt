package com.joaororiz.desafio.android.ui.base

import android.app.Application
import androidx.annotation.IntegerRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaororiz.desafio.android.R
import kotlinx.android.synthetic.main.list_item_user.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

// To avoid writing the same extensions multiple times, we'll make an abstract class for ViewModels
abstract class BaseViewModel : ViewModel() {

    fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

}
