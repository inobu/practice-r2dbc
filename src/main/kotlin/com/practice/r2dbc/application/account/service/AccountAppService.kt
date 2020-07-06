package com.practice.r2dbc.application.account.service

import com.practice.r2dbc.presentation.account.model.Account
import reactor.core.publisher.Flux

interface AccountAppService {
    fun findAll(): Flux<Account>
}
