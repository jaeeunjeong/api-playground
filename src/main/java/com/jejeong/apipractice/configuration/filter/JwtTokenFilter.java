package com.jejeong.apipractice.configuration.filter;

import com.jejeong.apipractice.sevice.sign.SignService;
import com.jejeong.apipractice.util.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  private final SignService signService;

  private final String secretKey;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (!header.startsWith("Bearer ")) {
      log.warn("Authorization Header does not start with Bearer");
      filterChain.doFilter(request, response);
    }

    final String token = header.split(" ")[1].trim();
    UserDetails userDetails = signService.loadUserByUserEmail(
        JwtTokenUtils.getUserEmail(token, secretKey));

    if (!JwtTokenUtils.validate(token, userDetails.getUsername(), secretKey)) {
      filterChain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());

    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    filterChain.doFilter(request, response);

  }
}
