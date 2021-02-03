package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.module.Scores;
import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import com.github.zmilad97.onlineExam.repository.ExamRepository;
import com.github.zmilad97.onlineExam.repository.QuestionRepository;
import com.github.zmilad97.onlineExam.repository.ScoreRepository;
import com.github.zmilad97.onlineExam.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;
    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    @Autowired
    public MasterController(UserRepository userRepository, ScoreRepository scoreRepository, ExamRepository examRepository, QuestionRepository questionRepository, ExamRepository examRepository1) {
        this.userRepository = userRepository;
        this.scoreRepository = scoreRepository;
        this.questionRepository = questionRepository;
        this.examRepository = examRepository1;
    }

    @GetMapping("result/{examId}")
    public ResponseEntity<List<Scores>> examResult(@PathVariable long examId) {
        if (examRepository.findExamById(examId).getMaker() == (SecurityUtil.getCurrentUser())
                || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))
            return ResponseEntity.ok(scoreRepository.findByExam(examRepository.findExamById(examId)));
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("question/{examId}")
    public List<Question> getQuestion(@PathVariable long examId) {
        if (examRepository.findExamById(examId).getMaker() == (SecurityUtil.getCurrentUser())
                || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))
            return questionRepository.findByExam(examRepository.findExamById(examId));
        return null;
    }

    @GetMapping("get-user")
    public Map<Long,String> getUsers(){
        Map<Long,String> usersMap = new HashMap<>();
        List<User> users = userRepository.findAll();
        users.forEach(user -> usersMap.put(user.getId(), user.getName()));
        return usersMap;
    }



}
