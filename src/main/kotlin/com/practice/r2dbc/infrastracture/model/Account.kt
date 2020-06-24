package com.practice.r2dbc.infrastracture.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("accounts")
class Account(
        @Id
        val id: Long,

        val name: String
) {
}
