package id.stefanusdany.data.datastore

import id.stefanusdany.data.data.remote.response.LoginResultResponse
import io.reactivex.Flowable

interface IUserPreference {
    fun getUserInfo(): Flowable<LoginResultResponse>

    fun login(userId: String, userName: String, token: String)

    fun logout()
}