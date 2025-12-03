package com.soyeon.mynmybackend.domain.auth.service;

import com.soyeon.mynmybackend.domain.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

      private final JavaMailSender mailSender;
      private final CacheManager cacheManager;
      private final UserRepository userRepository;

      @Value("${spring.mail.username}")
      private String fromEmail;

      public void sendAuthCode(String to) {

            verifyEmail(to);

            var message = mailSender.createMimeMessage();
            String code = getRandomCode();
            Cache cache = getEmailCodeCache();
            cache.put(to, code);

            try {
                  var helper = new MimeMessageHelper(message, false, "UTF-8");
                  helper.setFrom(fromEmail, "마이앤마이");
                  helper.setTo(to);
                  helper.setSubject("인증코드");
                  helper.setText("코드: " + code, false);

                  mailSender.send(message);
            }
            catch (MessagingException e) {
                  throw new RuntimeException("메일 발송 중 오류가 발생했습니다.", e);
            }
            catch (UnsupportedEncodingException e) {
                  throw new RuntimeException(e);
            }
      }

      public void verifyCode(String email, String code) {

            // 이메일 - 인증코드가 들어있는 캐쉬
            Cache codeCache = getEmailCodeCache();

            // 캐쉬에서 이메일에 해당하는 인증 코드 가져오기
            String originalCode = codeCache.get(email, String.class);

            // 인증코드 검증
            if (originalCode == null) throw new IllegalArgumentException("인증코드가 존재하지 않거나 만료되었습니다.");
            if (!originalCode.equals(code)) throw new IllegalArgumentException("인증코드가 일치하지 않습니다.");

            // 캐쉬에서 해당 이메일 - 인증코드 제거
            codeCache.evict(email);

            // 이메일 - 인증상태 캐쉬에 인증 완료 데이터 추가
            Cache statusCache = getEmailStatusCache();
            statusCache.put(email, true);
      }

      private void verifyEmail(String email) {
            log.info("이메일 확인: {}", email);
            if(userRepository.existsByEmail(email)){
                  log.warn("이미 존재하는 이메일 입니다.");
                  throw new IllegalArgumentException("이미 등론된 이메일 입니다.");
            }
      }

      private String getRandomCode() {
            SecureRandom random = new SecureRandom();
            int number = random.nextInt(1_000_000);
            return String.format("%06d", number);
      }

      private Cache getEmailCodeCache() {
            Cache cache = cacheManager.getCache("emailCode");
            if(cache == null) throw new IllegalStateException("이메일 - 인증코드 캐시를 찾을 수 없습니다.");
            return cache;
      }

      private Cache getEmailStatusCache() {
            Cache cache = cacheManager.getCache("emailStatus");
            if(cache == null) throw new IllegalStateException("이메일 - 인증상태 캐시를 찾을 수 없습니다.");
            return cache;
      }
}
