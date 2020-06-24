package com.practice.r2dbc.application.router

import com.practice.r2dbc.application.account.handler.AccountHandler
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Component
class Router(
        private val accountHandler: AccountHandler
) {
    companion object {
        private const val PATH = "/accounts"
    }

    @Bean
    fun route(): RouterFunction<ServerResponse> {
        return router {
            accept(MediaType.APPLICATION_JSON).nest {
                GET(PATH, accountHandler::account)
            }
        }
    }
}
