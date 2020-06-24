package com.practice.r2dbc.infrastracture.repository

import com.practice.r2dbc.infrastracture.model.Account
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface AccountRepository : ReactiveCrudRepository<Account, Long> {
}
