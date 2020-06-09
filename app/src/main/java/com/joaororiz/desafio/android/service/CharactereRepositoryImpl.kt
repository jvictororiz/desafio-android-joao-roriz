package com.joaororiz.desafio.android.service

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.joaororiz.desafio.android.data.entities.Character
import com.joaororiz.desafio.android.data.entities.Comic
import com.joaororiz.desafio.android.data.entities.DataWrapper
import com.joaororiz.desafio.android.data.entities.GlobalResponse
import com.joaororiz.desafio.android.data.service.MarvelService
import com.joaororiz.desafio.android.datasource.Listing
import com.joaororiz.desafio.android.datasource.factory.MainDataSourceFactory
import com.joaororiz.desafio.android.util.ext.backgroundCall
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.Executors


interface CharactereRepository {
    @MainThread
    fun listAll(): Listing<Character>

    suspend fun findComicsByCharactere(id: Int): DataWrapper<GlobalResponse<Comic>?>

    class CharactereRepositoryImpl(val service: MarvelService) : CharactereRepository {
        @MainThread
        override fun listAll(): Listing<Character> {
            val factory = MainDataSourceFactory(service, Executors.newSingleThreadExecutor())
            val livePagedList = LivePagedListBuilder(factory, 20).build()

            return Listing(
                pagedList = livePagedList,
                networkState = Transformations.switchMap(factory.mutableLiveData) { it.networkState },
                retry = { factory.mutableLiveData.value?.retryAllFailed() },
                refresh = { factory.mutableLiveData.value?.invalidate() }
            )
        }

        override suspend fun findComicsByCharactere(id: Int): DataWrapper<GlobalResponse<Comic>?> = service.findComics(id).backgroundCall(Dispatchers.IO)
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}

