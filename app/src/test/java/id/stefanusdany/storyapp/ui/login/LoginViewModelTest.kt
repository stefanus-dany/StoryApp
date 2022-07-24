package id.stefanusdany.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.stefanusdany.data.DataDummy
import id.stefanusdany.data.data.Result
import id.stefanusdany.data.data.remote.response.LoginResponse
import id.stefanusdany.data.getOrAwaitValue
import id.stefanusdany.data.repository.Repository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: id.stefanusdany.data.repository.Repository
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLogin = id.stefanusdany.data.DataDummy.generateDummyLoginResponse()
    private val email = "johnchampion@gmail.com"
    private val password = "password"

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(repository)
    }

    @Test
    fun `when login response should not null and return success`() {
        val expectedLogin = MutableLiveData<id.stefanusdany.data.data.Result<id.stefanusdany.data.data.remote.response.LoginResponse>>()
        expectedLogin.value = id.stefanusdany.data.data.Result.Success(dummyLogin)

        `when`(loginViewModel.login(email, password)).thenReturn(expectedLogin)

        val actualLogin = loginViewModel.login(email, password).getOrAwaitValue()

        Mockito.verify(repository).login(email, password)
        assertNotNull(actualLogin)
        assertTrue(actualLogin is id.stefanusdany.data.data.Result.Success)
    }

    @Test
    fun `when login response error should return error`() {
        val login = MutableLiveData<id.stefanusdany.data.data.Result<id.stefanusdany.data.data.remote.response.LoginResponse>>()
        login.value = id.stefanusdany.data.data.Result.Error("Error")
        `when`(loginViewModel.login(email, password)).thenReturn(login)
        val actualLogin = loginViewModel.login(email, password).getOrAwaitValue()
        Mockito.verify(repository).login(email, password)
        assertNotNull(actualLogin)
        assertTrue(actualLogin is id.stefanusdany.data.data.Result.Error)
    }

    @Test
    fun `when save user should call saveUser in repository`() {
        val expectedBoolean = MutableLiveData<Boolean>()
        expectedBoolean.value = true
        loginViewModel.saveUser("1", "stefanusdany", "12345token")
        Mockito.verify(repository).saveUser("1", "stefanusdany", "12345token")
    }

}