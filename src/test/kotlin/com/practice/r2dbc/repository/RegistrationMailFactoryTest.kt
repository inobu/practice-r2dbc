package com.practice.r2dbc.repository

import com.practice.r2dbc.domain.model.user.Password
import com.practice.r2dbc.domain.model.user.User
import com.practice.r2dbc.domain.model.user.UserId
import com.practice.r2dbc.domain.model.user.UserName
import com.practice.r2dbc.domain.type.MailAddress
import com.practice.r2dbc.infrastracture.RegistrationEmailFactoryImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class RegistrationMailFactoryTest {
    @Autowired
    lateinit var registrationEmailFactoryImpl: RegistrationEmailFactoryImpl

    @Test
    fun createEmail() {
        val user = User.of(
                UserId.of(1),
                UserName.of("testUser"),
                Password.of("123456Abc+"),
                MailAddress.of("test@example.com")
        )
        val email = registrationEmailFactoryImpl.createEmail(user)
        assert("project-firebase丸パクリ のメールアドレスの確認" == email.subject)
    }
}
