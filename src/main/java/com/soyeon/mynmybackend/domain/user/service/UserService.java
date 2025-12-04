package com.soyeon.mynmybackend.domain.user.service;

import com.soyeon.mynmybackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

      private final UserRepository userRepository;
      private final CacheManager cacheManager;

      public void create(String email, String password, String name, String nickname) {
            String verifiedEmail = verifyEmail(email);

      }

      private String verifyEmail(String email) {
            Cache cache = getEmailStatusCache();
            if(cache.get(email, Boolean.class) == null || !Boolean.TRUE.equals(cache.get(email, Boolean.class)))
                  throw new IllegalStateException("이메일 인증을 완료해주세요.");

            return email;
      }

      private Cache getEmailStatusCache() {
            Cache cache = cacheManager.getCache("emailStatus");
            if(cache == null) throw new IllegalStateException("이메일 - 인증상태 캐시를 찾을 수 없습니다.");
            return cache;
      }
}
