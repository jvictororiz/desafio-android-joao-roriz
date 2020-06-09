package com.joaororiz.desafio.android.usecase

import com.nhaarman.mockitokotlin2.whenever
import com.joaororiz.desafio.android.base.BaseTest
import com.joaororiz.desafio.android.data.dao.entities.UserTable
import com.joaororiz.desafio.android.data.entities.Character
import com.joaororiz.desafio.android.service.CharactereRepository
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock


class FindUsersUseCaseImplTest : BaseTest() {

    @Mock
    private lateinit var charactereRepository: CharactereRepository

    private lateinit var userUseCase: FindUsersUseCase


    @Before
    fun init() {
        userUseCase = FindUsersUseCaseImpl(charactereRepository)
    }

    @Test
    fun `when FindUsersUseCase calls listAllOnline with success then compare between expected and returned`() {
        val expectedUsers = listOf(Character("img", "name", 0, "username"))
        var resultUsers: ResultRest<List<Character>?>? = null
        runBlocking {
            whenever(charactereRepository.listAll()).thenReturn(ResultRest.success(expectedUsers))
            resultUsers = userUseCase.listAllUsers()
        }
        assertNotNull(resultUsers)
        resultUsers?.let {
            assertTrue(it.isSuccessful())
            assertEquals(expectedUsers, it.data)
        }
    }

    @Test
    fun `when FindUsersUseCase calls listAll with error then find offline`() {
        val expectedUsers = listOf(UserTable(0, "username", "img", "name"))
        var resultUsers: ResultRest<List<Character>?>? = null
        runBlocking {
            whenever(charactereRepository.listAll()).thenReturn(ResultRest.error("", 404))
            whenever(charactereRepository.listAllCache()).thenReturn(expectedUsers)
            resultUsers = userUseCase.listAllUsers()
        }
        assertNotNull(resultUsers)
        resultUsers?.let {
            assertFalse(it.isSuccessful())
            assertTrue(it.isCacheSuccessful())
            assertNotNull(it.data)
        }
    }

    @Test
    fun `when FindUsersUseCase calls listAll offline and has no data saved return error`()  {
        var resultUsers: ResultRest<List<Character>?>? = null
        runBlocking {
            whenever(charactereRepository.listAll()).thenReturn(ResultRest.error("", 404))
            whenever(charactereRepository.listAllCache()).thenReturn(null)
            resultUsers = userUseCase.listAllUsers()
        }
        //Assert
        assertNotNull(resultUsers)
        resultUsers?.let {
            assertFalse(it.isSuccessful())
            assertFalse(it.isCacheSuccessful())
            assertNull(it.data)
        }
    }


}