//package com.practice.r2dbc.config.jwt
//
//import java.util.ArrayList
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import io.jsonwebtoken.Jwts
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.ReactiveAuthenticationManager
//import org.springframework.security.core.context.SecurityContextHolder
//import java.io.IOException
//import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.web.server.authentication.AuthenticationWebFilter
//
//class JWTAuthorizationFilter : AuthenticationWebFilter {
//    constructor(authenticationManager: ReactiveAuthenticationManager): super(authenticationManager) {
//    }
//
//    override fun doFilterInternal(req: HttpServletRequest,
//                                  res: HttpServletResponse,
//                                  chain: FilterChain) {
//        val header = req.getHeader(HEADER_STRING)
//
//        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
//            chain.doFilter(req, res)
//            return
//        }
//
//        // AuthorizationヘッダのBearer Prefixである場合
//        val authentication = getAuthentication(req)
//
//        SecurityContextHolder.getContext().authentication = authentication
//        chain.doFilter(req, res)
//    }
//
//    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
//        val token = request.getHeader(HEADER_STRING)
//
//        if (token != null) {
//            // JWTをパース
//            val user = Jwts.parser()
//                    .setSigningKey(SECRET.toByteArray())
//                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//                    .getBody()
//                    .getSubject()
//
//            return if (user != null) {
//                // TODO : Credentials(第二引数)に電子署名を入れるようにする。
//                UsernamePasswordAuthenticationToken(user, null, ArrayList<GrantedAuthority>())
//            } else null
//        }
//
//        return null
//    }
//}
