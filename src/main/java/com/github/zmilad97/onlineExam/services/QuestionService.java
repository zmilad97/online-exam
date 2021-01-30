package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionService extends JpaRepository<Question,Long> {

    List<Question> findByExam(Exam exam);
    Question findQuestionById(long Id);
}
