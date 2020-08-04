package com.practice.r2dbc.domain.model.user

class UserId private constructor(value: Long) {
    companion object {
        fun of(value: Long): UserId {
            return UserId(value)
        }
    }
}
