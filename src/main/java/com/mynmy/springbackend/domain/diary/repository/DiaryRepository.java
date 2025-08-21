package com.mynmy.springbackend.domain.diary.repository;

import com.mynmy.springbackend.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {


}
