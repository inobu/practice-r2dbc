package com.practice.r2dbc.domain.model.user

import com.practice.r2dbc.domain.type.MailAddress

class User private constructor(
        val userId: UserId,
        val userName: UserName,
        val password: Password,
        val mailAddress: MailAddress
) {
}
