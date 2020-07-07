package com.practice.r2dbc.infrastracture.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("accounts")
class Account(
        @Id
        @Column("id")
        val id: Long = 0,

        @Column("name")
        val name: String,

        @Column("password")
        val password: String,

        @Column("loginId")
        val loginId: String,

        @Column("role")
        val role: String
) {
}
