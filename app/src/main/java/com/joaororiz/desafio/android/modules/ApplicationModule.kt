package com.joaororiz.desafio.android.modules

import android.content.Context
import com.google.gson.GsonBuilder
import com.joaororiz.desafio.android.BaseApplication
import com.joaororiz.desafio.android.data.service.MarvelService
import com.joaororiz.desafio.android.util.ext.md5
import com.joaororiz.desafio.android.util.resources.ResourceProvider
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT: Long = 15L
private const val READ_TIMEOUT: Long = 15L

val applicationModule = module {
    single { createHttpClient() }
    single { buildWebService<MarvelService>((androidApplication() as BaseApplication).getApiUrl(), get()) }
    single<Picasso> { picasso(androidContext(), get()) }
    single { ResourceProvider(androidApplication()) }
}


private fun picasso(context: Context, downloader: OkHttpClient) =
    Picasso.Builder(context)
        .downloader(OkHttp3Downloader(downloader))
        .loggingEnabled(true)
        .build()

inline fun <reified T> buildWebService(baseUrl: String, httpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(httpClient)
        .build()
    return retrofit.create(T::class.java)
}

fun createHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        addInterceptor {
            val ts = Date().time.toString()
            val apiKey = "916330231d85c1fac17c72bdda7132b3"
            val privateKey = "bf8a5721c65c0d4d6c9be7fefae3fa5ba67f8d7d"


            var request = it.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("ts", ts)
                .addQueryParameter("apikey", apiKey)
                .addQueryParameter("hash", (ts + privateKey + apiKey).md5())

                .build()
            request = request.newBuilder().url(url).build()
            it.proceed(request)
        }
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
    }.build()
}