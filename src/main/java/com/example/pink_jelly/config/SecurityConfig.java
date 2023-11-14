package com.example.pink_jelly.config;

import com.example.pink_jelly.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;

@Configuration
@EnableWebSecurity // Spring Security 설정을 시작
//@EnableGlobalMethodSecurity(prePostEnabled = true) // 메소드 단위로 경로 권한 부여
@Log4j2
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 자원 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 인증 or 인가에 대한 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("--------- Configure ---------");
        http.csrf().disable()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .usernameParameter("memberId")
                .passwordParameter("passwd")
                .defaultSuccessUrl("/main")
                .failureUrl("/login");
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/main")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me");
        http.authorizeRequests()
                .antMatchers("/", "/member/signup", "/member/welcome", "/member/sendConfirmMail",
                        "/member/matchConfirmKey", "/member/removeConfirmKey", "/member/checkMemberId").permitAll()
                .antMatchers("/member/**", "/profile/myProfile").authenticated()
                .anyRequest().permitAll();

        return http.build();
    }
}
