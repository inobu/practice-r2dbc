package com.practice.r2dbc.domain.model.user

interface UserRepository {
    fun save(user: User)
    fun save()

}
