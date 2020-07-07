package com.practice.r2dbc.presentation.singup.model

data class RegistrationModel(
        val name: String,
        val loginId: String,
        val password: String,
        val role: String
) {
}
