package com.gym.gym.config;

import com.gym.gym.security.CustomUserDetailsService;
import com.gym.gym.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
public class WebSecurityConfig {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    LogoutHandler logoutHandler;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(request -> request
                    .requestMatchers(HttpMethod.GET,"/gym/user/login").permitAll()
                    .requestMatchers(HttpMethod.POST,"/gym/trainees", "/gym/trainers").permitAll()
                    .anyRequest().authenticated()
            ).logout(logout -> logout
                    .logoutUrl("/gym/user/logout")
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler(
                            (request, response, authentication) -> SecurityContextHolder.clearContext()
                    )
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationEventPublisher eventPublisher) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setAuthenticationEventPublisher(eventPublisher);

        return providerManager;
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher
            (ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

}
