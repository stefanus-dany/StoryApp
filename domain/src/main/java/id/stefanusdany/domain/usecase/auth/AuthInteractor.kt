package id.stefanusdany.domain.usecase.auth

import javax.inject.Inject
import id.stefanusdany.core.helper.utils.Result
import id.stefanusdany.domain.model.auth.LoginModel
import id.stefanusdany.domain.model.auth.LoginResultModel
import id.stefanusdany.domain.model.auth.RegisterModel
import id.stefanusdany.domain.repository.IRepository
import io.reactivex.Flowable

class AuthInteractor @Inject constructor(private val repository: IRepository) : AuthUseCase {
    override fun saveUser(userId: String, userName: String, token: String) =
        repository.saveUser(userId, userName, token)

    override fun logout() = repository.logout()

    override fun getUserInfo(): Flowable<LoginResultModel> = repository.getUserInfo()

    override fun register(
        name: String,
        email: String,
        password: String
    ): Flowable<Result<RegisterModel>> = repository.register(name, email, password)

    override fun login(email: String, password: String): Flowable<Result<LoginModel>> =
        repository.login(email, password)
}