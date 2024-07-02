package com.example.contactlistback.security;

import com.example.contactlistback.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity // switch off the default web application security configuration and add your own
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

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * https://stackoverflow.com/questions/77504542/rewriting-a-spring-security-deprecated-authenticationmanager-httpsecurity
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        return authManagerBuilder.build();
    }


    /**
     * https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html#authorizeHttpRequests(org.springframework.security.config.Customizer)
     * https://github.com/spring-projects/spring-security/issues/12750
     * https://stackoverflow.com/questions/76761567/spring-security-configuration-update-for-springboot-version-3-x
     * https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
     * @param http
     * @return
     * @throws Exception
     */
    // https://stackoverflow.com/questions/52029258/understanding-requestmatchers-on-spring-security
    // https://stackoverflow.com/questions/42828687/unable-to-set-up-basic-authentication-with-spring-boot-rest-api
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("listings/").hasRole("USER") // specify which requests the spring
                                // security configuration will be applied to
                                .requestMatchers("users/add").permitAll()
                                .anyRequest().authenticated() //other URLs are only allowed authenticated users
                )
                .httpBasic(hbc -> hbc.authenticationEntryPoint(customAuthenticationEntryPoint));
        return http.build();
    }
}
