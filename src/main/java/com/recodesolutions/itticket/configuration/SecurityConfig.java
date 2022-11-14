package com.recodesolutions.itticket.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "**/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/**",
            "/webjars/**",
            "**/swagger-ui/",
            "**/swagger-ui/**",
            "/swagger-ui/**"
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
//                .authorizeHttpRequests(authorize -> authorize
//                        .antMatchers(AUTH_WHITELIST).permitAll()
//                        .anyRequest().authenticated()
//                )
                .authorizeRequests(authorize ->authorize.antMatchers(AUTH_WHITELIST).permitAll().antMatchers("/**").authenticated())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt()
                );
        return http.build();
    }
}