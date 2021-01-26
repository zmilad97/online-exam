package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ExamService extends JpaRepository<Exam, Long> {
    List<Exam> findByCategoryAndMaker(String category,User maker);

    List<Exam> findByGradeAndMaker(String grade,User maker);

    List<Exam> findAllByTitleContainsAndMaker(String title,User maker);

    List<Exam> findByMaker(User maker);

    Exam findExamById(long id);

    List<Exam> findByActiveTrueAndIdIn(List<Long> collect);

    List<Exam> findByCategory(String category);
    List<Exam> findByGrade (String grade);
    List<Exam> findByTitleContains (String title);
}
