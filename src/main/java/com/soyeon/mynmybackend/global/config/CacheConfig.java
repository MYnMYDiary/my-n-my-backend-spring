package com.soyeon.mynmybackend.global.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

      @Bean
      public CacheManager cacheManager() {
            CaffeineCacheManager cacheManager = new CaffeineCacheManager();

            // email - code
            cacheManager.registerCustomCache("emailCode",
                    Caffeine.newBuilder()
                            .expireAfterWrite(5, TimeUnit.MINUTES)
                            .maximumSize(10_000)
                            .build());
            // email - status
            cacheManager.registerCustomCache("emailStatus",
                    Caffeine.newBuilder()
                            .expireAfterWrite(10, TimeUnit.MINUTES)
                            .maximumSize(10_000)
                            .build());

            return cacheManager;
      }
}
