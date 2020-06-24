package com.practice.r2dbc.application.account.service

import com.practice.r2dbc.application.account.model.Account
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class AccountAppServiceImpl() : AccountAppService {
    override fun findAll(): Flux<Account> {
        val account = Account(1, "Tainou")

//        return listOf(account)
//
        return Flux.just(
                account
        )
    }
}
