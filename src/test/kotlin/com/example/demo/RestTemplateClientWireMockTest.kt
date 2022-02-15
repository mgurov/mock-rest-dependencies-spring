package com.example.demo

import com.example.demo.domain.User
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * This class tests the client in isolation from the rest of our app, but against a real API instance
 * The client is manually instantiated pointing it to a (test) instance of the real API server
 * as per https://wiremock.org/docs/junit-jupiter/
 */
class RestTemplateClientWireMockTest {

	val wireMockServer = WireMockServer(0).also { it.start() }

	private val restTemplateClient = RestTemplateClient("http://localhost:${wireMockServer.port()}")

	@Test
	fun `should be able to fetch an existing user`() {

		wireMockServer.stubFor(get("/users/2").willReturn(jsonResponse("""
			{
			    "data": {
			        "id": 2,
			        "email": "janet.weaver@reqres.in",
			        "first_name": "Janet",
			        "last_name": "Weaver Wiremocked",
			        "avatar": "https://reqres.in/img/faces/2-image.jpg"
			    },
			    "support": {
			        "url": "https://reqres.in/#support-heading",
			        "text": "To keep ReqRes free, this test contributes by not calling the actual servers!"
			    }
			}
		""".trimIndent(), 200)))

		val actual = restTemplateClient.fetchUser("2")

		assertThat(actual).isEqualTo(
			User(
				id = "2",
				email = "janet.weaver@reqres.in",
				name = User.Name(
					first = "Janet",
					last = "Weaver Wiremocked",
				),
			)
		)
	}

	@AfterEach
	fun stopWireMock() {
		wireMockServer.stop()
	}
}
