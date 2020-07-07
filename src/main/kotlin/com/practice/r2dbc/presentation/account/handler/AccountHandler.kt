package com.practice.r2dbc.presentation.account.handler

import com.practice.r2dbc.presentation.account.model.AccountRequest
import com.practice.r2dbc.application.account.service.AccountAppService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class AccountHandler(private val accountAppService: AccountAppService) {
    fun account(req: ServerRequest): Mono<ServerResponse> {
        val accounts = accountAppService.findAll()

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accounts, AccountRequest::class.java)
    }
}
