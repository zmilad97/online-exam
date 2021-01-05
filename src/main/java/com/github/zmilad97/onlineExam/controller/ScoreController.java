package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.Scores;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import com.github.zmilad97.onlineExam.services.QuestionService;
import com.github.zmilad97.onlineExam.services.ScoreService;
import com.github.zmilad97.onlineExam.services.ScoreServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RestController
@RequestMapping("/score/")
public class ScoreController {
    private final ScoreService scoreService;
    private final QuestionService questionService;
    private final ReadWriteLock readWriteLock;
    private final ScoreServiceClass scoreServiceClass;

    @Autowired
    public ScoreController(ScoreService scoreService, QuestionService questionService, ScoreServiceClass scoreServiceClass) {
        this.scoreService = scoreService;
        this.questionService = questionService;
        this.scoreServiceClass = scoreServiceClass;
        readWriteLock = new ReentrantReadWriteLock();
    }

    @PostMapping("take")
    public void takeAnswers(@RequestBody List<String> answers) {
        for (int i = 1; i < answers.size(); i++) {
            readWriteLock.readLock().lock();
            Scores scores = new Scores();
            scores.setExamId(Long.parseLong(answers.get(0)));
            scores.setQuestionId(questionService.findByExamId(answers.get(0)).get(i - 1).getId());
            scores.setAnswer(Integer.parseInt(answers.get(i)));
            scores.setUserId(SecurityUtil.getCurrentUser().getId());

            if (!(scoreService.existsByUserIdAndExamIdAndQuestionId(scores.getUserId(), scores.getExamId(), scores.getQuestionId())))
                scoreService.save(scores);
            readWriteLock.readLock().unlock();
        }
    }

    @GetMapping("result")
    public double result(@RequestBody long examId) {
    return scoreServiceClass.result(SecurityUtil.getCurrentUser().getId(),examId);
    }

}
