package com.practice.r2dbc

import com.practice.r2dbc.application.account.service.AccountAppService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class R2dbcApplicationTests {
	@Autowired
	lateinit var accountAppService: AccountAppService

	@Test
	fun contextLoads() {
		val a = accountAppService.findAll()
		println(a.blockFirst())
	}

}
