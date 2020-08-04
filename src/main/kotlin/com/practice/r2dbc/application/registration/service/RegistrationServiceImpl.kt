package com.practice.r2dbc.application.registration.service

import com.practice.r2dbc.domain.model.email.EmailSender
import com.practice.r2dbc.domain.model.email.RegistrationEmailFactory
import com.practice.r2dbc.domain.model.user.User
import org.springframework.stereotype.Service

@Service
class RegistrationServiceImpl(
        private val registrationEmailFactory: RegistrationEmailFactory,
        private val emailSender: EmailSender
) : RegistrationService {
    override fun signUp(user: User) {
        val registrationEmail = registrationEmailFactory.createEmail(user)
        emailSender.send(registrationEmail)
    }
}
