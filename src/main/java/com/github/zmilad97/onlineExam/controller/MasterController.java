package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.module.Scores;
import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import com.github.zmilad97.onlineExam.services.ExamService;
import com.github.zmilad97.onlineExam.services.QuestionService;
import com.github.zmilad97.onlineExam.services.ScoreService;
import com.github.zmilad97.onlineExam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/master/")
public class MasterController {
    private final UserService userService;
    private final ScoreService scoreService;
    private final QuestionService questionService;
    private final ExamService examService;

    @Autowired
    public MasterController(UserService userService, ScoreService scoreService, ExamService examService, QuestionService questionService, ExamService examService1) {
        this.userService = userService;
        this.scoreService = scoreService;
        this.questionService = questionService;
        this.examService = examService1;
    }

    @GetMapping("result/{examId}")
    public ResponseEntity<List<Scores>> examResult(@PathVariable long examId) {
        if (examService.findById(examId).getMakerId() == (SecurityUtil.getCurrentUser().getId())
                || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))
            return ResponseEntity.ok(scoreService.findByExamId(examId));
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("question/{examId}")
    public List<Question> getQuestion(@PathVariable long examId) {
        if (examService.findById(examId).getMakerId() == (SecurityUtil.getCurrentUser().getId())
                || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))
            return questionService.findByExamId(examId);
        return null;
    }

    @GetMapping("get-user")
    public Map<Long,String> getUsers(){
        Map<Long,String> usersMap = new HashMap<>();
        List<User> users = userService.findAll();
        users.forEach(user -> usersMap.put(user.getId(), user.getName()));
        return usersMap;
    }


}
