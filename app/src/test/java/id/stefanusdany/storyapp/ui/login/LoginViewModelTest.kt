package id.stefanusdany.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.stefanusdany.storyapp.DataDummy
import id.stefanusdany.storyapp.data.Result
import id.stefanusdany.storyapp.data.remote.response.LoginResponse
import id.stefanusdany.storyapp.getOrAwaitValue
import id.stefanusdany.storyapp.repository.Repository
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
    private lateinit var repository: Repository
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLogin = DataDummy.generateDummyLoginResponse()
    private val email = "johnchampion@gmail.com"
    private val password = "password"

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(repository)
    }

    @Test
    fun `when login response should not null and return success`() {
        val expectedLogin = MutableLiveData<Result<LoginResponse>>()
        expectedLogin.value = Result.Success(dummyLogin)

        `when`(loginViewModel.login(email, password)).thenReturn(expectedLogin)

        val actualLogin = loginViewModel.login(email, password).getOrAwaitValue()

        Mockito.verify(repository).login(email, password)
        assertNotNull(actualLogin)
        assertTrue(actualLogin is Result.Success)
    }

    @Test
    fun `when login response error should return error`() {
        val login = MutableLiveData<Result<LoginResponse>>()
        login.value = Result.Error("Error")
        `when`(loginViewModel.login(email, password)).thenReturn(login)
        val actualLogin = loginViewModel.login(email, password).getOrAwaitValue()
        Mockito.verify(repository).login(email, password)
        assertNotNull(actualLogin)
        assertTrue(actualLogin is Result.Error)
    }

    @Test
    fun `when save user should call saveUser in repository`() {
        val expectedBoolean = MutableLiveData<Boolean>()
        expectedBoolean.value = true
        loginViewModel.saveUser("1", "stefanusdany", "12345token")
        Mockito.verify(repository).saveUser("1", "stefanusdany", "12345token")
    }

}