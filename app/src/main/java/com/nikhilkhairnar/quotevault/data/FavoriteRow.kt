package com.nikhilkhairnar.quotevault.data
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteRow(
    val id: String,
    val user_id: String,
    val quote_id: String,
)
