package com.practice.r2dbc.security.jwt

import com.nimbusds.jose.JWSSigner

import com.nimbusds.jose.KeyLengthException

import com.nimbusds.jose.crypto.MACSigner


/**
 * Creates a JWTSigner using a simple secret string
 */
class JWTCustomSigner {
    var signer: JWSSigner? = null

    init {
        signer = try {
            MACSigner(JWTSecrets.DEFAULT_SECRET)
        } catch (e: KeyLengthException) {
            null
        }
    }
}
