package id.stefanusdany.storyapp.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.stefanusdany.data.DataDummy
import id.stefanusdany.data.data.Result
import id.stefanusdany.data.data.remote.response.RegisterResponse
import id.stefanusdany.data.getOrAwaitValue
import id.stefanusdany.data.repository.Repository
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: id.stefanusdany.data.repository.Repository
    private lateinit var registerViewModel: RegisterViewModel
    private val dummyRegister = id.stefanusdany.data.DataDummy.generateDummyRegisterResponse()
    private val name = "John Champion"
    private val email = "johnchampion@gmail.com"
    private val password = "password"

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(repository)
    }

    @Test
    fun `when register response should not null and return success`() {
        val expectedRegister = MutableLiveData<id.stefanusdany.data.data.Result<id.stefanusdany.data.data.remote.response.RegisterResponse>>()
        expectedRegister.value = id.stefanusdany.data.data.Result.Success(dummyRegister)

        Mockito.`when`(registerViewModel.register(name, email, password))
            .thenReturn(expectedRegister)

        val actualRegister = registerViewModel.register(name, email, password).getOrAwaitValue()

        Mockito.verify(repository).register(name, email, password)
        assertNotNull(actualRegister)
        assertTrue(actualRegister is id.stefanusdany.data.data.Result.Success)

    }

    @Test
    fun `when register response error should return error`() {
        val register = MutableLiveData<id.stefanusdany.data.data.Result<id.stefanusdany.data.data.remote.response.RegisterResponse>>()
        register.value = id.stefanusdany.data.data.Result.Error("Error")
        Mockito.`when`(registerViewModel.register(name, email, password)).thenReturn(register)
        val actualRegister = registerViewModel.register(name, email, password).getOrAwaitValue()
        Mockito.verify(repository).register(name, email, password)
        assertNotNull(actualRegister)
        assertTrue(actualRegister is id.stefanusdany.data.data.Result.Error)
    }

}