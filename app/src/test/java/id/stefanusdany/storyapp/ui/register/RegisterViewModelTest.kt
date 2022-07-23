package id.stefanusdany.storyapp.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.stefanusdany.storyapp.DataDummy
import id.stefanusdany.storyapp.data.Result
import id.stefanusdany.storyapp.data.remote.response.RegisterResponse
import id.stefanusdany.storyapp.getOrAwaitValue
import id.stefanusdany.storyapp.repository.Repository
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
    private lateinit var repository: Repository
    private lateinit var registerViewModel: RegisterViewModel
    private val dummyRegister = DataDummy.generateDummyRegisterResponse()
    private val name = "John Champion"
    private val email = "johnchampion@gmail.com"
    private val password = "password"

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(repository)
    }

    @Test
    fun `when register response should not null and return success`() {
        val expectedRegister = MutableLiveData<Result<RegisterResponse>>()
        expectedRegister.value = Result.Success(dummyRegister)

        Mockito.`when`(registerViewModel.register(name, email, password))
            .thenReturn(expectedRegister)

        val actualRegister = registerViewModel.register(name, email, password).getOrAwaitValue()

        Mockito.verify(repository).register(name, email, password)
        assertNotNull(actualRegister)
        assertTrue(actualRegister is Result.Success)

    }

    @Test
    fun `when register response error should return error`() {
        val register = MutableLiveData<Result<RegisterResponse>>()
        register.value = Result.Error("Error")
        Mockito.`when`(registerViewModel.register(name, email, password)).thenReturn(register)
        val actualRegister = registerViewModel.register(name, email, password).getOrAwaitValue()
        Mockito.verify(repository).register(name, email, password)
        assertNotNull(actualRegister)
        assertTrue(actualRegister is Result.Error)
    }

}