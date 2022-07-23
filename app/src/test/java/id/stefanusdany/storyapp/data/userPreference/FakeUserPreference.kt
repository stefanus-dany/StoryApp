package id.stefanusdany.storyapp.data.userPreference

import id.stefanusdany.storyapp.data.remote.response.LoginResultResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeUserPreference : IUserPreference {

    private val data = mutableMapOf<String, Any>(
        STATE_KEY to false,
        USER_ID to "",
        USER_NAME to "",
        TOKEN to "token12345"
    )

    override fun getUserInfo(): Flow<LoginResultResponse> {
        return flowOf(
            LoginResultResponse(
                data[USER_ID] as String,
                data[USER_NAME] as String,
                data[TOKEN] as String,
            )
        )
    }

    override suspend fun login(userId: String, userName: String, token: String) {
        data[STATE_KEY] = true
        data[USER_ID] = userId
        data[USER_NAME] = userName
        data[TOKEN] = token
    }

    override suspend fun logout() {
        data[STATE_KEY] = false
        data[USER_ID] = ""
        data[USER_NAME] = ""
        data[TOKEN] = ""
    }

    companion object {
        private const val STATE_KEY = "state_key"
        private const val USER_ID = "user_id"
        private const val USER_NAME = "user_name"
        private const val TOKEN = "token"
    }
}