package com.example.contactlistback.security;

import com.example.contactlistback.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // switch off the default web application security configuration and add your own
@EnableMethodSecurity // to switch off the default web application security configuration and add your own
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Meanwhile these methods allows all request without authentication
     */
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().anyRequest();
    }*/

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * The AuthenticationManager coordinates and manages the authentication flow,
     * allowing the application to delegate the authentication process to multiple providers.
     * Each provider can have authentication mechanisms, such as username/password, social login,
     * or multi-factor authentication.
     * When a user attempts to log in, the application delegates the authentication to the AuthenticationManager.
     * The AuthenticationManager then selects the appropriate AuthenticationProvider based on the request type and forwards the request to the configured provider.
     *
     * @param config AuthenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Define what criteria an incoming request must match before being forwarded to the controllers. We want the
     * following criteria:
     * The request URL path matching /auth/signup and /auth/login doesn't require authentication.
     * Any other request URL path must be authenticated.
     * The request is stateless, meaning every request must be treated as a new one,
     * even if it comes from the same client or has been received earlier.
     * Must use the custom authentication provider, and they must be executed before the authentication middleware.
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                // specify which requests the spring security configuration will be applied to
                                .requestMatchers("auth/signup").permitAll()
                                .requestMatchers("auth/login").permitAll()
                                .anyRequest().authenticated() // other URLs are only allowed authenticated users
                )
                .authenticationProvider(authenticationProvider)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
