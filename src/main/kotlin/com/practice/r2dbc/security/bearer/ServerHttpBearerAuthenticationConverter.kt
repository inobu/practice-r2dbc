package com.practice.r2dbc.security.bearer

import com.practice.r2dbc.security.jwt.AuthorizationHeaderPayload
import com.practice.r2dbc.security.jwt.JWTCustomVerifier
import com.practice.r2dbc.security.jwt.UsernamePasswordAuthenticationBearer
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Predicate;


class ServerHttpBearerAuthenticationConverter : ServerAuthenticationConverter {
    private val jwtVerifier: JWTCustomVerifier? = JWTCustomVerifier()

    companion object {
        private val BEARER: String? = "Bearer "
        private val matchBearerLength: Predicate<String?>? = Predicate { authValue ->
            authValue?.let { it.length > BEARER!!.length } ?: false
        }
        private val isolateBearerValue: Function<String?, Mono<String?>?>? = Function { authValue -> Mono.justOrEmpty(authValue?.substring(BEARER!!.length)) }
    }

    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> {
        return matchBearerLength?.let {
            isolateBearerValue?.let { it1 ->
                Mono.justOrEmpty(exchange)
                        .flatMap(AuthorizationHeaderPayload::extract)
                        .filter(it)
                        .flatMap(it1)
                        .flatMap { it2 -> it2?.let { it3 -> jwtVerifier?.check(it3) } }
                        .flatMap(UsernamePasswordAuthenticationBearer::create).log()
            }
        } ?: throw IllegalArgumentException()
    }
}
