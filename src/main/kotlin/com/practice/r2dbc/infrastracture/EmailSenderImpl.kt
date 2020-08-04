package com.practice.r2dbc.infrastracture

import com.practice.r2dbc.domain.model.email.Email
import com.practice.r2dbc.domain.model.email.EmailSender
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Component

@Component
class EmailSenderImpl(private val mailSender: MailSender) : EmailSender {
    override fun send(email: Email) {
        //FIXME 例外処理

        SimpleMailMessage().apply {
            setTo(email.to.value)
            setFrom(email.from.value)
            email.cc?.map { it.value }?.toTypedArray()?.let { setCc(*it) }
            email.bcc?.map { it.value }?.toTypedArray()?.let { setBcc(*it) }
            setSubject(email.subject)
            setText(email.text)
        }.run {
            mailSender.send(this)
        }
//        val cfg = Configuration(Configuration.VERSION_2_3_23).apply {
//            setDirectoryForTemplateLoading(File("src/main/resources/template"))
//        }
//
//        cfg.getTemplate("init_mail.ftl")


    }
}
