package com.practice.r2dbc.application.account.service

import com.practice.r2dbc.domain.model.user.Password
import com.practice.r2dbc.infrastracture.model.Account
import com.practice.r2dbc.presentation.account.model.AccountRequest
import com.practice.r2dbc.presentation.singup.model.RegistrationModel
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AccountAppService {
    fun findAll(): Flux<AccountRequest>

    fun signUp()

    fun registration(registrationModel: Mono<RegistrationModel>): Mono<Account>
}
