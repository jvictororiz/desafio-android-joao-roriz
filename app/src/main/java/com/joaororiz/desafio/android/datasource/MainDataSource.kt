package com.joaororiz.desafio.android.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.joaororiz.desafio.android.data.entities.Character
import com.joaororiz.desafio.android.data.service.MarvelService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class MainDataSource (val service: MarvelService, val retryExecutor: Executor) : PageKeyedDataSource<Int, Character>() {

    private var retry: (() -> Any)? = null

    val networkState = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Character>) {
        networkState.postValue(NetworkState.LOADING)

        try {
            val response = service.findCharacteres(20, 0).execute()
            retry = null
            callback.onResult(response.body()?.data?.results ?: listOf(), null, 20)
        } catch (e: Exception) {
            networkState.postValue(NetworkState.LOADED)
            retry = {
                loadInitial(params, callback)
            }
            networkState.postValue(NetworkState.ERROR("Erro ao buscar informações, favor verifique sua conexão"))
        }finally {
            networkState.postValue(NetworkState.LOADED)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        networkState.postValue(NetworkState.LOADING)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = service.findCharacteres(params.key, params.key + 20).execute()
                retry = null
                callback.onResult(response.body()?.data?.results ?: listOf(), params.key + 20)
            } catch (e: Exception) {
                networkState.postValue(NetworkState.LOADED)
                retry = {
                    loadAfter(params, callback)
                }
                networkState.postValue(NetworkState.ERROR(e.message))
            } finally {
                networkState.postValue(NetworkState.LOADED)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {}
}