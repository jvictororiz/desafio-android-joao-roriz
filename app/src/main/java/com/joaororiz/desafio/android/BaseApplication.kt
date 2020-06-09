package com.joaororiz.desafio.android

import android.app.Application
import com.joaororiz.desafio.android.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class BaseApplication : Application() {
    open fun getApiUrl(): String {
        return BuildConfig.SERVER_URL
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger()
            modules(
                listOf(
                    applicationModule,
                    repositoryModule,
                    viewModelModules
                )
            )
        }
    }
}