package com.example.demo.domain

data class User(
    val id: String,
    val email: String,
    val name: Name,
) {
    data class Name(
        val first: String,
        val last: String,
    )
}