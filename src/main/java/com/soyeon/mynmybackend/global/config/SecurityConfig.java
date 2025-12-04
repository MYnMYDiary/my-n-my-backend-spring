package com.soyeon.mynmybackend.global.config;

import com.soyeon.mynmybackend.global.exception.ErrorCode;
import com.soyeon.mynmybackend.global.exception.MynMyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

      @Bean
      public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/auth/**")
                        .permitAll()
                        .anyRequest().authenticated()
                    )
                    .formLogin(form -> form
                        .loginPage("/login")
                    )
                    .logout(logout -> logout
                        .logoutUrl("/logout")
                    )
                    .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                    );

                    return http.build();
      }

      @Bean
      public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
      }

      /** 인증되지 않은 사용자가 보호된 리소스에 접근할 때 호출 (예: 로그인 하지 않은 상태에서 API 호출) */
      private AuthenticationEntryPoint authenticationEntryPoint() {
            return (request, response, authException) -> {
                  throw new MynMyException(ErrorCode.UNAUTHORIZED);  // MynMyException 던지기
            };
      }


      /** - 인증은 됐지만, 해당 권한이 없을 때 호출됩니다. (예: 일반 유저가 admin API 접근 시) */
      public AccessDeniedHandler accessDeniedHandler() {
            return (request, response, accessDeniedException) -> {
                  throw new MynMyException(ErrorCode.FORBIDDEN);  // MynMyException 던지기
            };
      }

}
