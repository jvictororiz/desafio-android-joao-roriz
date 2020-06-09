package com.joaororiz.desafio.android.viewModel

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.joaororiz.desafio.android.base.BaseTest
import com.joaororiz.desafio.android.R
import com.joaororiz.desafio.android.data.entities.Character
import com.joaororiz.desafio.android.usecase.FindUsersUseCase
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock


class MainViewModelTest : BaseTest() {
    @Mock
    private lateinit var resultUsersObserver: Observer<List<Character>>
    @Mock
    private lateinit var alertOfflineObserver: Observer<Int>
    @Mock
    private lateinit var errorObserver: Observer<String>
    @Mock
    private lateinit var findUsersUseCase: FindUsersUseCase

    private lateinit var mainViewModel:MainViewModel


    @Before
    fun init(){
        mainViewModel = MainViewModel(findUsersUseCase)
    }

    @Test
    fun `when viewModel calls listAllUsers with success then sets resultUsers with success`() {
        runBlocking { whenever(findUsersUseCase.listAllUsers()).thenReturn(ResultRest.success(listOf())) }
        mainViewModel.resultUsersObserver.observeForever(resultUsersObserver)
        mainViewModel.findAllUser()
        //Assert
        verify(resultUsersObserver).onChanged(listOf())
        assertNotNull(mainViewModel.resultUsersObserver.value)
    }

    @Test
    fun `when viewModel calls listAll with error then sets error LiveData`() {
        val expectedError = "Erro de conexão"
        runBlocking { whenever(findUsersUseCase.listAllUsers()).thenReturn(ResultRest.error(expectedError)) }
        mainViewModel.errorObserver.observeForever(errorObserver)
        mainViewModel.resultUsersObserver.observeForever(resultUsersObserver)
        mainViewModel.findAllUser()
        //Assert
        verify(errorObserver).onChanged(expectedError)
        assertNull(mainViewModel.resultUsersObserver.value)
    }

    @Test
    fun `when viewmodel calls listAll offline then return the datas in base with sucess`()  {
        val expectedOfflineUsers = listOf(Character("img", "joao", 1, "joaov"))
        runBlocking {
            whenever(findUsersUseCase.listAllUsers()).thenReturn(ResultRest.cacheSuccess(expectedOfflineUsers, mock()))
        }
        mainViewModel.resultUsersObserver.observeForever(resultUsersObserver)
        mainViewModel.alertOfflineObserver.observeForever(alertOfflineObserver)
        mainViewModel.errorObserver.observeForever(errorObserver)
        mainViewModel.findAllUser()

        //Assert
        verify(alertOfflineObserver).onChanged(R.string.alert_offline)
        verify(resultUsersObserver).onChanged(expectedOfflineUsers)
    }


}