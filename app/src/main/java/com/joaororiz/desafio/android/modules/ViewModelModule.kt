package com.joaororiz.desafio.android.modules

import com.joaororiz.desafio.android.viewModel.ComicCharactersViewModel
import com.joaororiz.desafio.android.viewModel.DetailedCharactersViewModel
import com.joaororiz.desafio.android.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailedCharactersViewModel(get()) }
    viewModel { ComicCharactersViewModel(get()) }
}