package com.jejeong.apipractice.configuration;

import com.jejeong.apipractice.configuration.filter.JwtTokenFilter;
import com.jejeong.apipractice.sevice.member.MemberService;
import com.jejeong.apipractice.sevice.sign.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {

  private final SignService signService;

  @Value("${jwt.secret-key.access}")
  private String secretKey;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeHttpRequests
            (request -> request
                .requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .logout(withDefaults())
        .addFilterBefore(new JwtTokenFilter(signService, secretKey),
            UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }
}
