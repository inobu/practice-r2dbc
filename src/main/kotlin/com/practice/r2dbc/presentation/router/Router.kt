package com.practice.r2dbc.presentation.router

import com.practice.r2dbc.presentation.account.handler.AccountHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.function.server.*

@EnableWebFlux
@Configuration
class Router(
        private val accountHandler: AccountHandler
) {
    companion object {
        private const val PATH = "/accounts"
    }

    @Bean
    fun route(): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest{
            GET(PATH, accountHandler::account)
        }
    }
}
