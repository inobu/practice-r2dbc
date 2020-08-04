package com.practice.r2dbc.domain.model.user

class Password private constructor(val value: String) {
    companion object {
        fun of(password: String): Password {
            return Password(password)
        }
    }

    init {
        if (!this.value.matches(Regex(
                        "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!-/:-@\\[-`{-~])[!-~]{8,48}$"
                ))) {
            throw IllegalArgumentException()
        }
    }
}
