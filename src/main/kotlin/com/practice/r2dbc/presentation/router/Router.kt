package com.practice.r2dbc.presentation.router

import com.practice.r2dbc.presentation.account.handler.AccountHandler
import com.practice.r2dbc.presentation.singup.handler.SignupHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.function.server.*

@EnableWebFlux
@Configuration
class Router(
        private val accountHandler: AccountHandler,
        private val signupHandler: SignupHandler
) {
    companion object {
        private const val PATH = "/accounts"
        private const val REGISTRATION_PATH = "/registration"
    }

    @Bean
    fun route(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET(PATH, accountHandler::account)
            POST(REGISTRATION_PATH, signupHandler::registration)
        }
    }

}
