package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.security.SecurityUtil;
import com.github.zmilad97.onlineExam.services.ExamService;
import com.github.zmilad97.onlineExam.services.QuestionService;
import com.github.zmilad97.onlineExam.services.ScoreService;
import com.github.zmilad97.onlineExam.services.ScoreServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score/")
public class ScoreController {
    private final ScoreService scoreService;
    private final QuestionService questionService;
    private final ScoreServiceClass scoreServiceClass;
    private final ExamService examService;

    @Autowired
    public ScoreController(ScoreService scoreService, QuestionService questionService, ScoreServiceClass scoreServiceClass, ExamService examService) {
        this.scoreService = scoreService;
        this.questionService = questionService;
        this.scoreServiceClass = scoreServiceClass;
        this.examService = examService;
    }

    /**
     * client post their answers to this method
     *
     * @param answers send by user
     *
     */
    @PostMapping("take")
    public void takeAnswers(@RequestBody List<Long> answers) {
      scoreServiceClass.takeAnswers(answers);
    }

    @GetMapping("result")
    public double result(@RequestBody long examId) {
        return scoreServiceClass.result(SecurityUtil.getCurrentUser(), examService.findExamById(examId));
    }

}
