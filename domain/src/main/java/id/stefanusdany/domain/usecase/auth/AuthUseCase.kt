package id.stefanusdany.domain.usecase.auth

import id.stefanusdany.core.helper.utils.Result
import id.stefanusdany.domain.model.auth.LoginModel
import id.stefanusdany.domain.model.auth.LoginResultModel
import id.stefanusdany.domain.model.auth.RegisterModel
import io.reactivex.Flowable

interface AuthUseCase {

    fun saveUser(userId: String, userName: String, token: String)

    fun logout()

    fun getUserInfo(): Flowable<LoginResultModel>

    fun register(
        name: String,
        email: String,
        password: String
    ): Flowable<Result<RegisterModel>>

    fun login(email: String, password: String): Flowable<Result<LoginModel>>

}