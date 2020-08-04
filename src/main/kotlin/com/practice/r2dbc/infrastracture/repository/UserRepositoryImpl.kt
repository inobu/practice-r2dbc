package com.practice.r2dbc.infrastracture.repository

import com.practice.r2dbc.domain.model.user.User
import com.practice.r2dbc.domain.model.user.UserRepository
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(private val client: DatabaseClient) : UserRepository {

    override fun save(user: User) {
        client

                .insert()
                .into("registration")
                .value("mail_address", "a")
                .value("password", "a")
                .value("created_at", "2020-10-05")
                .value("updated_at", "2020-10-05")
                .then()
    }

    override fun save() {
        client
                .insert()
                .into("registration")
                .value("mail_address", "a")
                .value("password", "a")
                .value("created_at", "2020-10-05")
                .value("updated_at", "2020-10-05")
                .then()


    }
}
