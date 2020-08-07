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
        val templateText = getText("signup.ftl")
        val first = templateText.indexOf("\n")
        val subject = templateText.substring(0 until first)
        val text = templateText.substring(first)

        return Email.of(
                from = MailAddress.of("test@inobu.dev"),
                to = user.mailAddress,
                subject = subject,
                text = text
        )
    }


    fun getText(templateName: String, model: Any? = null): String {
        val template = configuration.getTemplate(templateName)
        return model?.let { FreeMarkerTemplateUtils.processTemplateIntoString(template, it) } ?: template.toString()
    }
}
