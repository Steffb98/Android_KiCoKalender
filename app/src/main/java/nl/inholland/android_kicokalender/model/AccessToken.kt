package nl.inholland.android_kicokalender.model

data class AccessToken (

    val accessToken : String,
    val tokenType : String,
    val expiresIn : Int
)