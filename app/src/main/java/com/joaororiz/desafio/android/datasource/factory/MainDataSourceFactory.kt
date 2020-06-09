package com.joaororiz.desafio.android.datasource.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.joaororiz.desafio.android.data.entities.Character
import com.joaororiz.desafio.android.data.service.MarvelService
import com.joaororiz.desafio.android.datasource.MainDataSource
import java.util.concurrent.Executor

class MainDataSourceFactory(val service: MarvelService, val retryExecutor: Executor): DataSource.Factory<Int, Character>() {
    val mutableLiveData = MutableLiveData<MainDataSource>()

    override fun create(): MainDataSource {
        val source = MainDataSource(service, retryExecutor)
        mutableLiveData.postValue(source)
        return source
    }
}