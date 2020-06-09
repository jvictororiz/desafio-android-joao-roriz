package com.joaororiz.desafio.android.datasource

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Listing<T>(
        var pagedList: LiveData<PagedList<T>>,
        val networkState: LiveData<NetworkState>,
        val refresh: () -> Unit,
        val retry: () -> Unit
)