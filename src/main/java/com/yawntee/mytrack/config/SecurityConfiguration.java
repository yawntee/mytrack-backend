package com.yawntee.mytrack.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.yawntee.mytrack.entity.User;
import com.yawntee.mytrack.pojo.Resp;
import com.yawntee.mytrack.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler, AuthenticationEntryPoint {

    private static final JsonMapper json = new JsonMapper();

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }

    private final UserService service;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.formLogin()
                .successHandler(this)
                .failureHandler(this)
                .and()
                .exceptionHandling().authenticationEntryPoint(this)
                .and()
                .logout().logoutSuccessHandler(this);
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder);
        http.authenticationProvider(provider);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        log.info("用户[" + user.getUsername() + "]登陆成功！");
        response.setContentType("application/json;charset=utf-8");
        Resp<Map<String, String>> resp = Resp.success(Map.of("name", user.getName(), "role", user.getRole().name()));
        PrintWriter out = response.getWriter();
        json.writeValue(out, resp);
        out.flush();
        out.close();
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String message = exception instanceof DisabledException ? "账户已被禁用" : exception.getMessage();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        json.writeValue(out, Resp.fail(message));
        out.flush();
        out.close();
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        json.writeValue(out, Resp.success());
        out.flush();
        out.close();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
    }
}
