package com.net.anthill.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


  @Bean
  public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
    http.csrf((csrf) -> csrf.disable())
        .headers((headers) -> headers.frameOptions((FrameOptionsConfig::sameOrigin)))//disable for h2 console
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/api/admin/**").hasAuthority(SystemAuthority.FULL_ACCESS.name())
            .requestMatchers("/api/**").authenticated()
            .anyRequest().permitAll());
    http.httpBasic(withDefaults());
    return http.build();


  }

}
