package com.practice.r2dbc.security.jwt

import com.nimbusds.jwt.SignedJWT
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import reactor.core.publisher.Mono
import java.text.ParseException
import java.util.stream.Collectors
import java.util.stream.Stream


object UsernamePasswordAuthenticationBearer {
    fun create(signedJWTMono: SignedJWT): Mono<Authentication> {
        val subject: String
        val auths: String
        val authorities: List<*>
        try {
            subject = signedJWTMono.jwtClaimsSet.subject
            auths = signedJWTMono.jwtClaimsSet.getClaim("roles") as String
        } catch (e: ParseException) {
            return Mono.empty()
        }
        authorities = Stream.of(*auths.split(",").toTypedArray())
                .map { a: String? -> SimpleGrantedAuthority(a) }
                .collect(Collectors.toList())
        return Mono.justOrEmpty(UsernamePasswordAuthenticationToken(subject, null, authorities))
    }
}
