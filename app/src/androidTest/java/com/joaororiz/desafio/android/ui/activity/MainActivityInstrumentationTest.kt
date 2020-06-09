package com.joaororiz.desafio.android.ui.activity

import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.joaororiz.desafio.android.base.BaseServiceInstrumentationTest
import com.joaororiz.desafio.android.R
import com.joaororiz.desafio.android.data.dao.UserDao
import com.joaororiz.desafio.android.data.dao.databaseConfig.AplicationDatabase
import com.joaororiz.desafio.android.data.dao.entities.UserTable
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityInstrumentationTest : BaseServiceInstrumentationTest() {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java, true, false)
    private lateinit var db: AplicationDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        db = Room.databaseBuilder(
            context,
            AplicationDatabase::class.java,
            "database-db-test"
        ).allowMainThreadQueries().build()
        userDao = db.userDao()
    }

    @Before
    fun finish(){
        userDao.clearAll()
    }

    @Test
    fun shouldDisplayTitle() {
        rule.launchActivity(null)
        val expectedTitle = context.getString(R.string.title)
        onView(withText(expectedTitle)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayError() {
        userDao.clearAll()
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return mockUnsuccessfulResponse()
            }
        }
        rule.launchActivity(null).apply {
            onView(withId(R.id.tv_error)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItem() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return mockSuccessfulResponse("/json/response_users.json")
            }
        }
        rule.launchActivity(null).apply {
            onView(withId(R.id.tv_error)).check(matches(not(isDisplayed())))
            onView(withText("Eduardo Santos")).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItemOffline() {
        userDao.saveAll(listOf(UserTable(0, "@eduardo.santos", "https://randomuser.me/api/portraits/men/9.jpg", "Eduardo Santos")))
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return mockUnsuccessfulResponse()
            }
        }
        rule.launchActivity(null).apply {
            onView(withId(R.id.tv_error)).check(matches(not(isDisplayed())))
            onView(withId(R.id.rv_personagens)).check(matches(isDisplayed()))
            isToastMessageDisplayed(rule.activity, R.string.alert_offline)
        }
    }
}