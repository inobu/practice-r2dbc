package com.practice.r2dbc.infrastracture.repository

import com.practice.r2dbc.infrastracture.model.Account
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface AccountRepository : ReactiveCrudRepository<Account, Long> {

    fun findAccountByName(name: String): Mono<Account>

    fun findAccountByLoginId(loginId: String): Mono<Account>
}
