package com.example.pink_jelly.config;

import com.example.pink_jelly.security.CustomUserDetailService;
import com.example.pink_jelly.security.handler.Custom403Handler;
import com.example.pink_jelly.security.handler.CustomSocialLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
@EnableGlobalMethodSecurity(prePostEnabled = true) // 메소드 단위로 경로 권한 부여
@Log4j2
public class CustomSecurityConfig {
    // 주입 필요
    private final DataSource dataSource;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // 자동로그인
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 파일 경로에 시큐리티 적용을 안함
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomSocialLoginSuccessHandler(passwordEncoder());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    // 인증 or 인가에 대한 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("--------- Configure ---------");
        // CSRF 토큰 비활성화
        http.csrf().disable();

        http.headers().frameOptions().sameOrigin();

        http.authenticationProvider(daoAuthenticationProvider());

        //  커스텀 로그인 페이지
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .usernameParameter("memberId")
                .passwordParameter("passwd")
                .defaultSuccessUrl("/")
                .failureUrl("/login");

        // 자동로그인
        http.rememberMe()
                .key("12345678")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(customUserDetailService)
                .tokenValiditySeconds(60 * 60 * 24 * 30);

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/main")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me");

        http.authorizeRequests()
                .antMatchers("/", "/member/signup", "/member/welcome", "/member/sendConfirmMail",
                        "/member/matchConfirmKey", "/member/removeConfirmKey", "/member/checkMemberId").permitAll()
                .antMatchers("/member/**", "/profile/myProfile", "/main/write", "/main/modify", "/catsMe/board/write", "/catsMe/board/modify",
                        "/catsMe/review/write", "/catsMe/review/modify", "/chat/**").authenticated()
                .anyRequest().permitAll();


        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()); // 403

        http.oauth2Login().loginPage("/login").successHandler(authenticationSuccessHandler());

        return http.build();
    }

}
