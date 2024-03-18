package com.example.custard.config

import com.example.custard.config.auth.jwt.JwtAccessDeniedHandler
import com.example.custard.config.auth.jwt.JwtAuthenticationEntryPoint
import com.example.custard.config.auth.jwt.JwtAuthenticationFilter
import com.example.custard.config.auth.oauth2.CustomOAuth2UserService
import com.example.custard.config.auth.oauth2.handler.OAuth2LoginFailureHandler
import com.example.custard.config.auth.oauth2.handler.OAuth2LoginSuccessHandler
import com.example.custard.domain.user.model.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsUtils

@Configuration
class SecurityConfig (
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler,
    private val oAuth2LoginFailureHandler: OAuth2LoginFailureHandler,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    ) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors {}
            .csrf { it.disable() }
            .headers { it.frameOptions { it.disable() } }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { request ->
                request
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .requestMatchers("/", "/css/**", "/images/**", "/js/**").permitAll()
                    .requestMatchers("/api/**").hasRole(Role.ADMIN.name)
                    .requestMatchers("/api/oauth2/authorize/**", "/oauth2/callback/**").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login { it ->
                it.authorizationEndpoint {
                    it.baseUri("/api/oauth2/authorize")
                }
                it.redirectionEndpoint {
                    it.baseUri("/oauth2/callback/*")
                }
                it.userInfoEndpoint {
                    it.userService(customOAuth2UserService)
                }
                it.successHandler(oAuth2LoginSuccessHandler)
                it.failureHandler(oAuth2LoginFailureHandler)
            }
            .logout {
                it.logoutRequestMatcher {
                    it.servletPath.equals("/api/logout")
                }
            }
            .exceptionHandling {
                it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                it.accessDeniedHandler(jwtAccessDeniedHandler)
            }

        return http
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}