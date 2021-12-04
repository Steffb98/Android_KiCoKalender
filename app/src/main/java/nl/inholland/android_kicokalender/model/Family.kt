package nl.inholland.android_kicokalender.model

import java.io.Serializable
import java.util.*

data class Family (

    val id : UUID? = null,
    val users : List<User>? = null,
    val folders : List<String>? = null,
    val name : String? = "",
    val partitionKey : UUID? = null
): Serializable
