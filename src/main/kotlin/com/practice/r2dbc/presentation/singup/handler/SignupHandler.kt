package com.practice.r2dbc.presentation.singup.handler

import com.practice.r2dbc.application.account.service.AccountAppService
import com.practice.r2dbc.infrastracture.model.Account
import com.practice.r2dbc.presentation.singup.model.RegistrationModel
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class SignupHandler(private val accountAppService: AccountAppService) {
    fun registration(req: ServerRequest): Mono<ServerResponse> {
        val mono = req.bodyToMono(RegistrationModel::class.java)
        val account =accountAppService.registration(mono)

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(account, Account::class.java)
    }
}
