package com.culture.ticketing.common.config;

import com.culture.ticketing.user.domain.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/bookings/**").hasRole(Role.USER.name())
                .antMatchers("/api/v1/places/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/v1/show-areas/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/api/v1/show-areas/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/v1/show-area-grades/**").hasRole(Role.ADMIN.name())
                .antMatchers( "/api/v1/shows/**/likes").hasRole(Role.USER.name())
                .antMatchers(HttpMethod.POST, "/api/v1/shows/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/v1/show-seats/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/api/v1/show-seats/**").hasRole(Role.USER.name())
                .antMatchers(HttpMethod.POST, "/api/v1/performers/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/v1/rounds/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/api/v1/rounds/detail").hasRole(Role.USER.name())
                .antMatchers("/api/v1/round-performers/**").hasRole(Role.ADMIN.name())
                .antMatchers("/api/v1/users/profile").hasRole(Role.USER.name())
                .anyRequest().permitAll();
        return http.build();
    }
}
