package com.joaororiz.desafio.android.datasource

sealed class NetworkState {
    object LOADING : NetworkState()
    object LOADED : NetworkState()
    class ERROR(val msg: String?) : NetworkState()
}