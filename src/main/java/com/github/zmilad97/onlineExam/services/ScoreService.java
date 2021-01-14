package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.module.Scores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreService extends JpaRepository<Scores,List<Long>> {

    boolean existsByUserIdAndExamIdAndQuestionId(Long userId,Long examId,Long questionId);
    Scores findByUserIdAndExamIdAndQuestionId(Long userId,Long examId,Long questionId);
    Scores findByUserIdAndExamId(Long userId,Long examId);
    List<Scores> findByExamId(long examId);
}

