package com.practice.r2dbc.application.account.service

import com.practice.r2dbc.infrastracture.model.Account
import com.practice.r2dbc.presentation.account.model.AccountRequest
import com.practice.r2dbc.infrastracture.repository.AccountRepository
import com.practice.r2dbc.presentation.singup.model.RegistrationModel
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AccountAppServiceImpl(private val accountRepository: AccountRepository) : AccountAppService {
    override fun findAll(): Flux<AccountRequest> {
        return accountRepository.findAll().map { AccountRequest(it.id, it.name) }
    }

    @Transactional
    override fun registration(registrationModel: Mono<RegistrationModel>):Mono<Account> {
        val account = registrationModel.map {
            Account(name = it.name,
                    loginId = it.loginId,
                    password = encode(it.password),
                    role = it.role
            )
        }
        return account.flatMap { accountRepository.save(it) }
    }

    private fun encode(rawPassword: String): String {
        return BCryptPasswordEncoder().encode(rawPassword)
    }
}
