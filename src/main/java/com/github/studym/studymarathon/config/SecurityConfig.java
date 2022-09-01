package com.github.studym.studymarathon.config;

import com.github.studym.studymarathon.config.security.filter.ApiCheckFilter;
import com.github.studym.studymarathon.config.security.filter.ApiLoginFilter;
import com.github.studym.studymarathon.config.security.handler.ApiLoginFailHandler;
import com.github.studym.studymarathon.config.security.handler.MemberLoginSuccessHandler;
import com.github.studym.studymarathon.config.security.service.AuthUserDetailsService;
import com.github.studym.studymarathon.config.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {


    @Autowired
    private AuthUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
/*        http.authorizeRequests().antMatchers("/api/title").permitAll();
        http.authorizeRequests().antMatchers("/api/main").hasRole("USER");
        http.authorizeRequests().antMatchers("api/admin").hasRole("ADMIN");*/


        http.formLogin();
        http.csrf().disable();
        http.logout();
        http.oauth2Login().successHandler(successHandler());
        http.rememberMe().tokenValiditySeconds(60 * 10).userDetailsService(userDetailsService);//10분짜리 로그인 유지 토큰

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);



        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(apiLoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public MemberLoginSuccessHandler successHandler() {
        return new MemberLoginSuccessHandler(passwordEncoder());
    }


    @Bean
    public ApiLoginFilter apiLoginFilter(AuthenticationManager authenticationManager) throws Exception {

        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login", jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager);

        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());

        return apiLoginFilter;
    }

    @Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/member/**/*", jwtUtil());
    }


}


