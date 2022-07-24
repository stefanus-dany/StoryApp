package id.stefanusdany.data

object DataDummy {

    fun generateDummyListStoryResponse(): List<id.stefanusdany.data.data.remote.response.ListStoryResponse> {
        val items: MutableList<id.stefanusdany.data.data.remote.response.ListStoryResponse> =
            arrayListOf()
        for (i in 0..100) {
            val story = id.stefanusdany.data.data.remote.response.ListStoryResponse(
                i.toString(),
                "name + $i",
                "desc $i",
                "photoUrl $i",
                "createdAt $i",
                i.toDouble(),
                i.toDouble()
            )
            items.add(story)
        }
        return items
    }

    fun generateDummyLoginResponse(): id.stefanusdany.data.data.remote.response.LoginResponse {
        return id.stefanusdany.data.data.remote.response.LoginResponse(
            false,
            "success",
            id.stefanusdany.data.data.remote.response.LoginResultResponse(
                "user-yj5pc_LARC_AgK61",
                "Arif Faizin",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTdQYjNXNzM1UXZiY3JhVk4iLCJpYXQiOjE2NTA3NzI5ODB9.jIxrscIU2_Wd82GuPhwjgf-chO3SA_vrPVsZT0H-ZyM"
            )
        )
    }

    fun generateDummyRegisterResponse(): id.stefanusdany.data.data.remote.response.RegisterResponse {
        return id.stefanusdany.data.data.remote.response.RegisterResponse(
            false,
            "User Created"
        )
    }

    fun generateDummyAddStoryResponse(): id.stefanusdany.data.data.remote.response.FileUploadResponse {
        return id.stefanusdany.data.data.remote.response.FileUploadResponse(
            false,
            "success"
        )
    }

    fun generateDummyStoryResponse(): id.stefanusdany.data.data.remote.response.StoryResponse {
        val items: MutableList<id.stefanusdany.data.data.remote.response.ListStoryResponse> =
            arrayListOf()
        for (i in 0..100) {
            val story = id.stefanusdany.data.data.remote.response.ListStoryResponse(
                i.toString(),
                "name + $i",
                "desc $i",
                "photoUrl $i",
                "createdAt $i",
                i.toDouble(),
                i.toDouble()
            )
            items.add(story)
        }
        return id.stefanusdany.data.data.remote.response.StoryResponse(
            false,
            "Stories fetched successfully",
            items
        )
    }

    fun generateDummyUserInfoResponse(): id.stefanusdany.data.data.remote.response.LoginResultResponse {
        return id.stefanusdany.data.data.remote.response.LoginResultResponse(
            "userId",
            "John Champion",
            "token12345"
        )
    }

    fun generateLogoutResponse(): id.stefanusdany.data.data.remote.response.LoginResultResponse {
        return id.stefanusdany.data.data.remote.response.LoginResultResponse(
            "",
            "",
            ""
        )
    }

    fun generateSaveUserInfoResponse(): id.stefanusdany.data.data.remote.response.LoginResultResponse {
        return id.stefanusdany.data.data.remote.response.LoginResultResponse(
            "userId",
            "John Champion",
            "token12345"
        )
    }

}