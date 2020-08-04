package com.practice.r2dbc.domain.model.email

import com.practice.r2dbc.domain.model.user.User

interface RegistrationEmailFactory {
    fun createEmail(user: User): Email
}
