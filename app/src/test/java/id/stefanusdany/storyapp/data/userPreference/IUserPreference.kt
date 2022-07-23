package id.stefanusdany.storyapp.data.userPreference

import id.stefanusdany.storyapp.data.remote.response.LoginResultResponse
import kotlinx.coroutines.flow.Flow

interface IUserPreference {
    fun getUserInfo(): Flow<LoginResultResponse>
    suspend fun login(userId: String, userName: String, token: String)
    suspend fun logout()
}