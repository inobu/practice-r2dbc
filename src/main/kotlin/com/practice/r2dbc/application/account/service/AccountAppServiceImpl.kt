package com.practice.r2dbc.application.account.service

import com.practice.r2dbc.infrastracture.model.Account
import com.practice.r2dbc.presentation.account.model.AccountRequest
import com.practice.r2dbc.infrastracture.repository.AccountRepository
import com.practice.r2dbc.presentation.singup.model.RegistrationModel
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AccountAppServiceImpl(
        private val accountRepository: AccountRepository,
        private val mailSender: JavaMailSender
) : AccountAppService {
    override fun findAll(): Flux<AccountRequest> {
        return accountRepository.findAll().map { AccountRequest(it.id, it.name) }
    }

    override fun signUp() {
        val message = SimpleMailMessage().apply {
            setFrom("test@test.jp")
            setTo("yabuno4@gmail.com")
            setSubject("test subject")
            setText("test body")
        }
        try {
            mailSender.send(message)
        } catch (e: MailException) {
            e.printStackTrace()
        }
    }


    @Transactional
    override fun registration(registrationModel: Mono<RegistrationModel>): Mono<Account> {
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
