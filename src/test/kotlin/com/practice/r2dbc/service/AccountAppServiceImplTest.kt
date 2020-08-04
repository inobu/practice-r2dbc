package com.practice.r2dbc.service

import com.practice.r2dbc.application.account.service.AccountAppService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class AccountAppServiceImplTest {
    @Autowired
    lateinit var accountAppService: AccountAppService

    @Test
    fun a() {
        accountAppService.signUp()
    }
}
