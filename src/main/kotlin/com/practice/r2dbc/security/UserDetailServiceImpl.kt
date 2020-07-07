package com.practice.r2dbc.security

import com.practice.r2dbc.infrastracture.repository.AccountRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserDetailServiceImpl(
        private val accountRepository: AccountRepository
) : ReactiveUserDetailsService {
    override fun findByUsername(username: String): Mono<UserDetails> {
        val account = accountRepository.findAccountByLoginId(username)

        return account.map {
            User
                    .withUsername(it.loginId)
                    .password(it.password)
                    .roles(it.role)
                    .build()
        }
    }
}
