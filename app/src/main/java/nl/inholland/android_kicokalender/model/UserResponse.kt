package nl.inholland.android_kicokalender.model

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime
import java.util.*

@JsonClass(generateAdapter = true)
data class UserResponse(
    val address: String,
    val age: LocalDateTime,
    val created: LocalDateTime,
    val email: String,
    val familyId: UUID,
    val firstName: String,
    val id: UUID,
    val lastName: String,
    val password: String,
    val postcode: String,
    val role: Role
)