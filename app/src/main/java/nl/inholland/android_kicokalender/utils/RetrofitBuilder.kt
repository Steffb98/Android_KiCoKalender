package nl.inholland.android_kicokalender.utils

import nl.inholland.android_kicokalender.repository.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://kicocalender.azurewebsites.net/api/"
    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    fun buildService(): RetrofitService {
        return builder.create(RetrofitService::class.java)
    }
}