package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
//Actually this is not a real service class, it is a repository class and should be named ExamRepository
public interface ExamService extends JpaRepository<Exam, Long> {
     List<Exam> findByCategory(String category) ;
     List<Exam> findByGrade(String grade);
     List<Exam> findAllByTitleContains(String title);
     List<Exam> findByMakerId(long id);
     List<Exam> findByActiveTrue();
     List<Exam> findByActiveTrueAndIdIn(List<Long> ids);
     Exam findById(long id);


}
