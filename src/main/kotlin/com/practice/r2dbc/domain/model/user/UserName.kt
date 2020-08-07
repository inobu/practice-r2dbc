package com.practice.r2dbc.domain.model.user

class UserName private constructor(val value: String) {
    companion object {
        fun of(userName: String): UserName {
            return UserName(userName)
        }
    }
}
