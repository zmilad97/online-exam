package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.repository.ExamRepository;
import com.github.zmilad97.onlineExam.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository, ExamRepository examRepository) {
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
    }

    @PostMapping("/add/{examId}")
    public void add(@RequestBody Question question, @PathVariable Long examId) {
        question.setExam(examRepository.findExamById(examId));
        questionRepository.save(question);
    }


}
