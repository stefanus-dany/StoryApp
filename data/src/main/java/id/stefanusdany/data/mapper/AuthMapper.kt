package id.stefanusdany.data.mapper

import id.stefanusdany.data.data.remote.response.LoginResponse
import id.stefanusdany.data.data.remote.response.LoginResultResponse
import id.stefanusdany.data.data.remote.response.RegisterResponse
import id.stefanusdany.domain.model.auth.LoginModel
import id.stefanusdany.domain.model.auth.LoginResultModel
import id.stefanusdany.domain.model.auth.RegisterModel

object AuthMapper {

    fun fromLoginResultResponseToModel(input: LoginResultResponse): LoginResultModel =
        LoginResultModel(
            userId = input.userId,
            name = input.name,
            token = input.token
        )

    fun fromRegisterResponseToModel(input: RegisterResponse): RegisterModel =
        RegisterModel(
            error = input.error,
            message = input.message
        )

    fun fromLoginResponseToModel(input: LoginResponse): LoginModel =
        LoginModel(
            error = input.error,
            message = input.message,
            result = fromLoginResultResponseToModel(input.result)
        )

}