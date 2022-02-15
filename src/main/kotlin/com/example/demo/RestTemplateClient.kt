package com.example.demo

import com.example.demo.domain.User
import com.fasterxml.jackson.annotation.JsonAlias
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component

@Component
class RestTemplateClient(
    apiBase: String = "https://reqres.in/api", //see https://reqres.in
) {

    val restTemplate = RestTemplateBuilder().rootUri(apiBase).build()

    fun fetchUser(userId: String): User? {
        val result = restTemplate.getForEntity("/users/{userId}",  Wrapper::class.java, userId)
        return result.body?.data?.toDomain()
    }

}

private data class Wrapper(
    val data: DataUser,
)

private data class DataUser(
    val id: String,
    val email: String,
    @JsonAlias("first_name")
    val firstName: String,
    @JsonAlias("last_name")
    val lastName: String,
) {
    fun toDomain(): User = User(
        id = id,
        email = email,
        name = User.Name(
            first = firstName,
            last = lastName,
        )
    )
}