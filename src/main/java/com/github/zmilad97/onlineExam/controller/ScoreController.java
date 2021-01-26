package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.security.SecurityUtil;
import com.github.zmilad97.onlineExam.services.ExamService;
import com.github.zmilad97.onlineExam.services.QuestionService;
import com.github.zmilad97.onlineExam.services.ScoreService;
import com.github.zmilad97.onlineExam.services.ScoreServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/score/")
public class ScoreController {
    private final ScoreServiceClass scoreServiceClass;
    private final ExamService examService;

    @Autowired
    public ScoreController(ScoreServiceClass scoreServiceClass, ExamService examService) {
        this.scoreServiceClass = scoreServiceClass;
        this.examService = examService;
    }

    /**
     * client post their answers to this method
     *
     * @param answers send by user
     *                answer[0] = examId
     */
    @PostMapping("take")
    public void takeAnswers(@RequestBody List<Long> answers) {
        scoreServiceClass.takeAnswers(answers);
    }

    @GetMapping("result/{examId}")
    public double result(@PathVariable long examId) {
        return scoreServiceClass.result(SecurityUtil.getCurrentUser(), examService.findExamById(examId));
    }

    @GetMapping("user-answer/{examId}")
    public Map<Integer, Long> getUserAnswer(@PathVariable Long examId) {
        return scoreServiceClass.getUserAnswers(examId);
    }

}
