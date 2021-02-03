package com.github.zmilad97.onlineExam.repository;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.module.Scores;
import com.github.zmilad97.onlineExam.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Scores,List<Long>> {

    boolean existsByUserAndExamAndQuestion(User user, Exam exam, Question question);
    Scores findByUserAndExamAndQuestion(User user, Exam exam, Question question);
    Scores findByUserAndExam(User user, Exam exam);
    List<Scores> findByExam(Exam exam);
}

