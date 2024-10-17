package com.einarr07.curso_spring_boot.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Value("${service.security.secure-key-username}")
    private String SECURITY_KEY_USERNAME;

    @Value("${service.security.secure-key-password}")
    private String SECURITY_KEY_PASSWORD;

    @Value("${service.security.secure-key-username-2}")
    private String SECURITY_KEY_USERNAME_2;

    @Value("${service.security.secure-key-password-2}")
    private String SECURITY_KEY_PASSWORD_2;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
                AuthenticationManagerBuilder.class
        );

        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser(SECURITY_KEY_USERNAME)
                .password(passwordEncoder().encode(SECURITY_KEY_PASSWORD))
                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"))
                .and()
                .withUser(SECURITY_KEY_USERNAME_2)
                .password(passwordEncoder().encode(SECURITY_KEY_PASSWORD_2))
                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DEV"));

        return http.authorizeRequests()
                .anyRequest()
                .hasRole("ADMIN")
                .and()
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> {})
                .build();
    }


}
