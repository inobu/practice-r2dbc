package com.practice.r2dbc.domain.model.email

import com.practice.r2dbc.domain.type.MailAddress

class Email private constructor
(
        val to: MailAddress,
        val from: MailAddress,
        val cc: List<MailAddress>?,
        val bcc: List<MailAddress>?,
        val subject: String,
        val text: String
) {
    companion object {
        fun of(
                to: MailAddress,
                from: MailAddress,
                cc: List<MailAddress>? = null,
                bcc: List<MailAddress>? = null,
                subject: String,
                text: String
        ): Email {
            return Email(to, from, cc, bcc, subject, text)
        }
    }
}


