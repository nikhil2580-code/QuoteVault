package com.nikhilkhairnar.quotevault.data
import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    val id: String,
    val text: String,
    val author: String,
    val category: String,
    val isFavorite: Boolean = false
)

