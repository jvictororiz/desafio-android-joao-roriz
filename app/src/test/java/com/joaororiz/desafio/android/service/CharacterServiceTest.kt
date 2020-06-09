package com.joaororiz.desafio.android.service

import com.joaororiz.desafio.android.base.BaseServiceTest
import com.joaororiz.desafio.android.data.service.MarvelService
import org.junit.Assert.*
import org.junit.Test


internal class CharacterServiceTest : BaseServiceTest() {

    @Test
    fun `Must list all users  successfully`() {
        val mockResponse = mockSuccessfulResponse("/json/response_users.json")
        mockWebServer.enqueue(mockResponse)
        val response = buildWebServiceTest<MarvelService>().getUsers().execute()
        assertNotNull(response.body())
        assertTrue(response.isSuccessful)
    }

    @Test
    fun `Must list all users  in error`() {
        val mockResponse = mockUnsuccessfulResponse()
        mockWebServer.enqueue(mockResponse)
        val response = buildWebServiceTest<MarvelService>().getUsers().execute()
        assertNull(response.body())
        assertFalse(response.isSuccessful)
    }
}
