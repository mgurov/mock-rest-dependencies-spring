package com.example.demo

import org.springframework.boot.web.client.RestTemplateBuilder

class SpringBootClient {

    val apiClient = RestTemplateBuilder().build()

}