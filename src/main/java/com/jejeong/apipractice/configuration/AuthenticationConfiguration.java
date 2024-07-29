package com.jejeong.apipractice.configuration;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import com.jejeong.apipractice.service.user.UserService;
import com.jejeong.apipractice.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfiguration {

  private final UserService userService;
  private final BCryptPasswordEncoder encoder;
  private final JwtTokenUtils jwtTokenUtils;

  @Value("${jwt.token.secret-key.access}")
  private String accessSecretKey;

  @Value("${jwt.token.secret-key.refresh}")
  private String refreshSecretKey;

  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(userService).passwordEncoder(encoder);
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {

    return web -> web.ignoring()
        .requestMatchers(
            antMatcher("/"),
            antMatcher("/favicon.ico"),
            antMatcher("/manifest.json"))
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        .requestMatchers(
            HttpMethod.POST, "/api/*/users/sign-up", "/api/*/users/sign-in");
  }

  @Bean
  public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(req -> req
            .anyRequest().authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        .addFilterBefore(new JwtTokenFilter(userService, accessSecretKey, jwtTokenUtils),
//            UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
