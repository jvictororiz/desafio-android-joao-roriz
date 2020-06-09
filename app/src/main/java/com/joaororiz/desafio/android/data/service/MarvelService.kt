package com.joaororiz.desafio.android.data.service

import com.joaororiz.desafio.android.data.entities.Character
import com.joaororiz.desafio.android.data.entities.Comic
import com.joaororiz.desafio.android.data.entities.DataWrapper
import com.joaororiz.desafio.android.data.entities.GlobalResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MarvelService {

    @GET("characters")
    fun findCharacteres(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<DataWrapper<GlobalResponse<Character>>>

    @GET("characters/{characterId}/comics")
    fun findComics(@Path("characterId") characterId:Int ): Call<DataWrapper<GlobalResponse<Comic>>>
}