package com.intern.asset;

import com.intern.asset.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AppConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                                .requestMatchers("/ping2").permitAll()
                                .requestMatchers("/http").permitAll()
                                .requestMatchers("/port").hasAuthority("ROLE_MANAGER")
                                .anyRequest().authenticated()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 服务器不会创建或存储用户的 session，适用于 JWT 认证模式，每个请求都必须携带 Token。
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // JwtAuthenticationFilter 在 UsernamePasswordAuthenticationFilter 之前执行，用于解析 JWT 令牌并验证用户身份
        // 先让 jwtAuthenticationFilter 检查 请求里的 Token 是否有效，如果有效就放行
        return http.build();
    }

    @Bean // 认证提供者
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // 创建一个 DAO 认证提供者，它是 Spring Security 自带的 基于数据库用户信息 进行认证的 Provider。
        authProvider.setUserDetailsService(userDetailsService); // 检查当前用户是否存在
        authProvider.setPasswordEncoder(passwordEncoder); // 检查密码是否正确
        return authProvider;
    }

    // 即使你把方法名改成 myCustomAuthProvider，Spring 依然能识别它，因为：
    // @Bean 会把返回的对象注册到 Spring 容器（ApplicationContext） 里。
    // Spring 通过 类型 (AuthenticationProvider.class) 而不是 方法名 来查找 Bean。

    @Bean // 认证管理器：选择了 DaoAuthenticationProvider。因为 Spring Security 会发现有一个 AuthenticationProvider，就会自动使用它
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean // 密码加密器
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
