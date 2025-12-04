package com.soyeon.mynmybackend.global.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseIdEntity extends BaseEntity{

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      @Column(updatable = false, nullable = false)
      private Long id;

}
