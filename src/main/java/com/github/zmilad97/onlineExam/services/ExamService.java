package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ExamService extends JpaRepository<Exam, Long> {
    List<Exam> findByCategory(String category);

    List<Exam> findByGrade(String grade);

    List<Exam> findAllByTitleContains(String title);

    List<Exam> findByMaker(User maker);

    Exam findExamById(long id);

    List<Exam> findByActiveTrueAndIdIn(List<Long> collect);
}
