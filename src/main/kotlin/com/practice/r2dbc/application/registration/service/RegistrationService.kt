package com.practice.r2dbc.application.registration.service

import com.practice.r2dbc.domain.model.user.User

interface RegistrationService {
    fun signUp(user: User)
}
