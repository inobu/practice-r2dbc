package com.practice.r2dbc.security

import com.practice.r2dbc.security.basic.BasicAuthenticationSuccessHandler
import com.practice.r2dbc.security.bearer.BearerTokenReactiveAuthenticationManager
import com.practice.r2dbc.security.bearer.ServerHttpBearerAuthenticationConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.codec.HttpMessageReader
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractor
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*
import java.util.function.Function


@Component
@Configuration
@EnableWebFluxSecurity
class WebFluxSecurityConfig {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity,
                                  authenticationManager: ReactiveAuthenticationManager,
                                  serverCodecConfigurer: ServerCodecConfigurer): SecurityWebFilterChain {
        http
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()

        val authenticationFilter = authenticationWebFilter(
                authenticationManager,
                serverCodecConfigurer,
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login")
        )

        val authorizeWebFilter = authorizeWebFilter(
                ServerWebExchangeMatchers.pathMatchers("/accounts")
        )

        http.addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION).
                authorizeExchange().pathMatchers("/registration").permitAll()
        http.addFilterAt(authorizeWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange().pathMatchers("/accounts").authenticated()

        return http.build()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }


    // @Bean は付けない (WebFilter が 2 重で登録されてしまう)
    fun authenticationWebFilter(
            authenticationManager: ReactiveAuthenticationManager,
            serverCodecConfigurer: ServerCodecConfigurer,
            loginPath: ServerWebExchangeMatcher
    ): AuthenticationWebFilter? {
        return AuthenticationWebFilter(authenticationManager).apply {
            // 認証処理を行うリクエスト
            setRequiresAuthenticationMatcher(loginPath)
            // 認証処理における認証情報を抽出方法
            setAuthenticationConverter(JsonBodyAuthenticationConverter(serverCodecConfigurer.readers))
            // 認証成功/失敗時の処理
            setAuthenticationSuccessHandler(BasicAuthenticationSuccessHandler())
//            setAuthenticationSuccessHandler(SuccessHandler())
            setAuthenticationFailureHandler(FailureHandler())
            // セキュリティコンテキストの保存方法
            setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        }
    }

    fun authorizeWebFilter(
            authorizePath: ServerWebExchangeMatcher
    ): AuthenticationWebFilter? {
        return AuthenticationWebFilter(BearerTokenReactiveAuthenticationManager()).apply {
            // 認可処理を行うリクエスト
            setRequiresAuthenticationMatcher(authorizePath)
            // 認証処理における認証情報を抽出方法
            setServerAuthenticationConverter(ServerHttpBearerAuthenticationConverter())
//            // 認証成功/失敗時の処理
//            setAuthenticationSuccessHandler(BasicAuthenticationSuccessHandler())
            setAuthenticationFailureHandler(FailureHandler())
//            // セキュリティコンテキストの保存方法
//            setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        }
    }

    // authenticationManager をカスタマイズしない場合には不要
    @Bean
    fun authenticationManager(
            userDetailsService: ReactiveUserDetailsService,
            passwordEncoder: PasswordEncoder
    ): ReactiveAuthenticationManager {
        return UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService).apply {
            setPasswordEncoder(passwordEncoder) // 設定しない場合は DelegatingPasswordEncoder になる
        }
    }

    // ステータスコード FORBIDDEN、本文は空でレスポンスを返す
    inner class EntryPoint : ServerAuthenticationEntryPoint {
        override fun commence(exchange: ServerWebExchange, e: AuthenticationException): Mono<Void> {
            return Mono.fromRunnable {
                exchange.response.statusCode = HttpStatus.FORBIDDEN
            }
        }
    }

    // ステータスコード FORBIDDEN、本文は空でレスポンスを返す
    inner class FailureHandler : ServerAuthenticationFailureHandler {
        override fun onAuthenticationFailure(
                webFilterExchange: WebFilterExchange,
                exception: AuthenticationException
        ): Mono<Void> = Mono.fromRunnable {
            webFilterExchange.exchange.response.statusCode = HttpStatus.FORBIDDEN
        }
    }

    // 想定するリクエストの本文は { "mail_address": "user@example.com", "password": "123456" } といった JSON
    inner class JsonBodyAuthenticationConverter(
            val messageReaders: List<HttpMessageReader<*>>
    ) : Function<ServerWebExchange, Mono<Authentication>> {

        override fun apply(exchange: ServerWebExchange): Mono<Authentication> {
            return BodyExtractors.toMono(AuthenticationInfo::class.java)
                    .extract(exchange.request, object : BodyExtractor.Context {
                        override fun messageReaders(): List<HttpMessageReader<*>> = messageReaders
                        override fun serverResponse(): Optional<org.springframework.http.server.reactive.ServerHttpResponse> = Optional.of(exchange.response)
                        override fun hints(): Map<String, Any> = mapOf()
                    })
                    .map { it.toToken() }
        }
    }

    // 認証リクエスト本文の JSON
    data class AuthenticationInfo(
            val loginId: String,
            val password: String
    ) {
        fun toToken() = UsernamePasswordAuthenticationToken(loginId, password)
    }

}
