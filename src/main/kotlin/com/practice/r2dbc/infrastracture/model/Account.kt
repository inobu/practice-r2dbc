package com.practice.r2dbc.infrastracture.model

import org.springframework.data.annotation.Id


class Account(
        @Id
        val id: Long,

        val name: String
) {
}
