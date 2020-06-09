package com.joaororiz.desafio.android

import com.joaororiz.desafio.android.ui.activity.MainActivityInstrumentationTest
import com.joaororiz.desafio.android.data.dao.CharacterDaoTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityInstrumentationTest::class,
    CharacterDaoTest::class

)
class MainActivityInstrumentationTestFlow