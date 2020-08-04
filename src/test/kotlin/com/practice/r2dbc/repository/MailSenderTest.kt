package com.practice.r2dbc.repository

import com.practice.r2dbc.domain.model.email.EmailSender
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class MailSenderTest {
    @Autowired
    lateinit var mailSender: EmailSender


    @Test
    fun sendTest() {
//        mailSender.send()
    }
}
