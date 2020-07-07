package com.practice.r2dbc.security.jwt

import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSVerifier
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.SignedJWT
import reactor.core.publisher.Mono
import java.text.ParseException
import java.time.Instant
import java.util.*
import java.util.function.Predicate


/**
 * Decides when a JWT string is valid.
 * First  try to parse it, then check that
 * the signature is correct.
 * If something fails an empty Mono is returning
 * meaning that is not valid.
 * Verify that expiration date is valid
 */
class JWTCustomVerifier {
    lateinit var jwsVerifier: JWSVerifier
    fun check(token: String): Mono<SignedJWT> {
        return Mono.justOrEmpty(createJWS(token))
                .filter(isNotExpired)
                .filter(validSignature)
    }

    private val isNotExpired: Predicate<SignedJWT> = Predicate { token -> getExpirationDate(token)?.after(Date.from(Instant.now())) ?: false
    }
    private val validSignature: Predicate<SignedJWT> = Predicate { token ->
        try {
            token.verify(jwsVerifier)
        } catch (e: JOSEException) {
            e.printStackTrace()
            false
        }
    }

    private fun buildJWSVerifier(): MACVerifier? {
        return try {
            MACVerifier(JWTSecrets.DEFAULT_SECRET)
        } catch (e: JOSEException) {
            e.printStackTrace()
            null
        }
    }

    private fun createJWS(token: String): SignedJWT? {
        return try {
            SignedJWT.parse(token)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    private fun getExpirationDate(token: SignedJWT): Date? {
        return try {
            token.jwtClaimsSet
                    .expirationTime
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    init {
        jwsVerifier = buildJWSVerifier()!!
    }
}
