package com.joaororiz.desafio.android.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.joaororiz.desafio.android.data.dao.databaseConfig.AplicationDatabase
import com.joaororiz.desafio.android.data.dao.entities.UserTable
import junit.framework.Assert.assertEquals
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CharacterDaoTest{


    private lateinit var db: AplicationDatabase
    private lateinit var dao: UserDao
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(context, AplicationDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.userDao()
    }

    @After
    fun shutdown() = db.close()

    @Test
    fun testSaveAndFindUsersData() {
        val usersExpected = listOf(UserTable(0, "username", "img","joao"))
        dao.saveAll(usersExpected)
        val usersRecovered = dao.findAll()
        assertEquals(usersExpected, usersRecovered)

    }
}