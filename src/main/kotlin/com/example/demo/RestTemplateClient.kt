package com.example.demo

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component

@Component
class RestTemplateClient(
    apiBase: String = "https://reqres.in/api",
) {

    val restTemplate = RestTemplateBuilder().rootUri(apiBase).build()

    fun fetchUser(userId: String): User? {
        val result = restTemplate.getForEntity("/users/{userId}", User::class.java, userId)
        return result.body
    }

}

data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
)