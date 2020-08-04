package com.practice.r2dbc.infrastracture

import com.practice.r2dbc.domain.model.email.Email
import com.practice.r2dbc.domain.model.email.RegistrationEmailFactory
import com.practice.r2dbc.domain.model.user.User
import com.practice.r2dbc.domain.type.MailAddress
import freemarker.template.Configuration
import org.springframework.stereotype.Component
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils

@Component
class RegistrationEmailFactoryImpl(private val configuration: Configuration) : RegistrationEmailFactory {
    override fun createEmail(user: User): Email {

        return Email.of(
                from = MailAddress.of("test@inobu.dev"),
                to = user.mailAddress,
                subject = "あ",
                text = "本文"
        )
    }

    private fun getText(templateName: String, model: Any): String {
        val template = configuration.getTemplate(templateName)
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model)
    }
}
