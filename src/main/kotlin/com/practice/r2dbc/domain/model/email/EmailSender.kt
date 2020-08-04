package com.practice.r2dbc.domain.model.email

import com.practice.r2dbc.domain.model.email.Email

interface EmailSender {
    fun send(email: Email)
}
