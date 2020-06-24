package com.practice.r2dbc.application.account.service

import com.practice.r2dbc.application.account.model.Account
import com.practice.r2dbc.infrastracture.repository.AccountRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class AccountAppServiceImpl(private val accountRepository: AccountRepository) : AccountAppService {
    override fun findAll(): Flux<Account> {
        return accountRepository.findAll().map { Account(it.id, it.name) }
    }
}
