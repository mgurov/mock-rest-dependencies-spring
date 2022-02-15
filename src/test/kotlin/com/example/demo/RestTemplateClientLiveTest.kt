package com.example.demo

import com.example.demo.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * This class tests the client in isolation from the rest of our app, but against a real API instance
 * The client is manually instantiated pointing it to a (test) instance of the real API server
 */
class RestTemplateClientLiveTest {

	private val restTemplateClient = RestTemplateClient()

	@Test
	fun `should be able to fetch an existing user`() {
		val actual = restTemplateClient.fetchUser("2")

		assertThat(actual).isEqualTo(
			User(
				id = "2",
				email = "janet.weaver@reqres.in",
				name = User.Name(
					first = "Janet",
					last = "Weaver",
				),
			)
		)
	}
}
