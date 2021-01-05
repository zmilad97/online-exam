package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionService extends JpaRepository<Question,Long> {

    List<Question> findByExamId(String examId);
    Question findQuestionById(Long Id);
}
