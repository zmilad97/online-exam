package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.Scores;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import com.github.zmilad97.onlineExam.repository.ExamRepository;
import com.github.zmilad97.onlineExam.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/score/")
public class ScoreController {
    private final ScoreService scoreService;
    private final ExamRepository examRepository;

    @Autowired
    public ScoreController(ScoreService scoreService, ExamRepository examRepository) {
        this.scoreService = scoreService;
        this.examRepository = examRepository;
    }

    /**
     * client post their answers to this method
     *
     * @param answers <questionId,userAnswer>
     */
    @PostMapping("take/{examId}")
    public void takeAnswers(@RequestBody Map<Long,Long> answers, @PathVariable long examId) {
        scoreService.takeAnswers(answers,examId);
    }

    @GetMapping("result/{examId}")
    public double result(@PathVariable long examId) {
        return scoreService.result(SecurityUtil.getCurrentUser(), examRepository.findExamById(examId));
    }

    /**
     * this method returns user Scores of an exam
     * @param examId send by Master or Admin
     * @return list of Scores
     */

    @GetMapping("user-scores/{examId}")
    public List<Scores> getUserAnswer(@PathVariable Long examId) {
        return scoreService.getUsersScores(examId);
    }

}
