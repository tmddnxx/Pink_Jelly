package com.example.pink_jelly.config;

import com.example.pink_jelly.admin.service.AdminUserDetailsService;
import com.example.pink_jelly.security.CustomUserDetailService;
import com.example.pink_jelly.security.handler.Custom403Handler;
import com.example.pink_jelly.security.handler.CustomSocialLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // Spring Security 설정을 시작
@RequiredArgsConstructor
@Log4j2
@Order(1)
public class AdminSecurityConfig {
    // 주입 필요
    private final AdminUserDetailsService adminUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {

        // CSRF 비활성화
        http.csrf().disable();

        http.authenticationProvider(adminAuthenticationProvider())
                .antMatcher("/admin/**")
                .authorizeRequests()
                .anyRequest().hasRole("ADMIN");

        http.formLogin()
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/loginProc")
                .usernameParameter("adminId")
                .defaultSuccessUrl("/admin/main")
                .permitAll();

        http.logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login")
                .invalidateHttpSession(true);

        http.exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/admin/login"));

        return http.build();
    }

}
