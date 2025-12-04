package com.soyeon.mynmybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MynmybackendApplication {

      public static void main(String[] args) {
            SpringApplication.run(MynmybackendApplication.class, args);
      }

}
