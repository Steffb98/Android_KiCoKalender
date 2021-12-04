package nl.inholland.android_kicokalender.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@JsonClass(generateAdapter = true)
data class User(
    @field:Json(name = "id") val id : String? = "00000000-0000-0000-0000-000000000000",
    @field:Json(name = "familyId")val familyId : String? = null,
    @field:Json(name = "email")var email : String? = "",
    @field:Json(name = "password")var password : String? = "",
    @field:Json(name = "firstName")var firstName : String? = "",
    @field:Json(name = "lastName")var lastName : String? = "",
    @field:Json(name = "age")val age : String? = "2000-10-10T00:00:00",
    @field:Json(name = "role")var role : String? = "",
    @field:Json(name = "address")var address : String? = "",
    @field:Json(name = "postcode")var postcode : String? = "",
    @field:Json(name = "created")val created : String? = "2021-12-04T10:32:08.8621059+00:00"
) : Serializable
