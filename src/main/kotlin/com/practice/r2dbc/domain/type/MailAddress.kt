package com.practice.r2dbc.domain.type

class MailAddress private constructor(val value: String) {
    companion object {
        fun of(value: String): MailAddress {
            return MailAddress(value)
        }
    }
}
