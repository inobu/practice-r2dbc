package com.practice.r2dbc.security.basic

import com.practice.r2dbc.security.jwt.JWTTokenService
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
class BasicAuthenticationSuccessHandler : ServerAuthenticationSuccessHandler {
    /**
     * A successful authentication object us used to create a JWT object and
     * added in the authorization header of the current WebExchange
     *
     * @param webFilterExchange
     * @param authentication
     * @return
     */
    override fun onAuthenticationSuccess(webFilterExchange: WebFilterExchange, authentication: Authentication): Mono<Void> = Mono.fromRunnable {
        webFilterExchange.exchange.response.statusCode = HttpStatus.OK
        webFilterExchange.exchange.response.headers.add(HttpHeaders.AUTHORIZATION, getHttpAuthHeaderValue(authentication))
    }

    companion object {
        private fun getHttpAuthHeaderValue(authentication: Authentication): String {
            return java.lang.String.join(" ", "Bearer", tokenFromAuthentication(authentication))
        }

        private fun tokenFromAuthentication(authentication: Authentication): String {
            return JWTTokenService.generateToken(
                    authentication.name,
                    authentication.credentials,
                    authentication.authorities)
        }
    }
}
