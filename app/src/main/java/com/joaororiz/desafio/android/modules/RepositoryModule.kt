package com.joaororiz.desafio.android.modules

import com.joaororiz.desafio.android.service.CharactereRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CharactereRepository> {
        CharactereRepository.CharactereRepositoryImpl(service = get())
    }
}