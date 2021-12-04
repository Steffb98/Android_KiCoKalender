package nl.inholland.android_kicokalender.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
enum class Role {
    Parent, Child, Mediator
}