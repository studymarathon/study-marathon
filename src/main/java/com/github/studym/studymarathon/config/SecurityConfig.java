package com.github.studym.studymarathon.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // 내부적으로 테스트용으로 만든 계정 비밀번호는 1234
    public InMemoryUserDetailsManager user() {
        UserDetails Test = User.withUsername("test")
                .password("$2a$10$2rtpooUPPWxaYN0k/JFkyOAx6iraltYJikdwZT303g2SLvHTgSqKO")
                .roles("USER").build();

        return new InMemoryUserDetailsManager(Test);
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/main").hasRole("USER");


        http.formLogin();
        http.csrf().disable();
        http.logout();

        return http.build();
    }
}
