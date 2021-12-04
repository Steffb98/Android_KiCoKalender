package nl.inholland.android_kicokalender.repository


import nl.inholland.android_kicokalender.model.AccessToken
import nl.inholland.android_kicokalender.model.LoginUser
import nl.inholland.android_kicokalender.model.User
import nl.inholland.android_kicokalender.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @POST("login")
    fun loginUser(
        @Body user: LoginUser
    ): Call<AccessToken>

    @POST("user")
    fun registerUser(
        @Body user: User
    ): Call<UserResponse>
}