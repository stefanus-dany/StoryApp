package id.stefanusdany.storyapp

import id.stefanusdany.storyapp.data.remote.response.*

object DataDummy {

    fun generateDummyListStoryResponse(): List<ListStoryResponse> {
        val items: MutableList<ListStoryResponse> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryResponse(
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

    fun generateDummyLoginResponse(): LoginResponse {
        return LoginResponse(
            false,
            "success",
            LoginResultResponse(
                "user-yj5pc_LARC_AgK61",
                "Arif Faizin",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTdQYjNXNzM1UXZiY3JhVk4iLCJpYXQiOjE2NTA3NzI5ODB9.jIxrscIU2_Wd82GuPhwjgf-chO3SA_vrPVsZT0H-ZyM"
            )
        )
    }

    fun generateDummyRegisterResponse(): RegisterResponse {
        return RegisterResponse(
            false,
            "User Created"
        )
    }

    fun generateDummyAddStoryResponse(): FileUploadResponse {
        return FileUploadResponse(
            false,
            "success"
        )
    }

    fun generateDummyStoryResponse(): StoryResponse {
        val items: MutableList<ListStoryResponse> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryResponse(
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
        return StoryResponse(
            false,
            "Stories fetched successfully",
            items
        )
    }

    fun generateDummyUserInfoResponse(): LoginResultResponse {
        return LoginResultResponse(
            "userId",
            "John Champion",
            "token12345"
        )
    }

    fun generateLogoutResponse(): LoginResultResponse {
        return LoginResultResponse(
            "",
            "",
            ""
        )
    }

    fun generateSaveUserInfoResponse(): LoginResultResponse {
        return LoginResultResponse(
            "userId",
            "John Champion",
            "token12345"
        )
    }

}