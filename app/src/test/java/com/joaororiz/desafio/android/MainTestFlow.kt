package com.joaororiz.desafio.android

import com.joaororiz.desafio.android.service.CharacterServiceTest
import com.joaororiz.desafio.android.viewModel.MainViewModelTest
import com.joaororiz.desafio.android.usecase.FindUsersUseCaseImplTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainViewModelTest::class,
    FindUsersUseCaseImplTest::class,
    CharacterServiceTest::class
)
class MainActivityTest