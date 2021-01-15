package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CorrectionService {
    private final ExamService examService;
    private final QuestionService questionService;
    private final UserService userService;
    private final ScoreService scoreService;

    @Autowired
    public CorrectionService(UserService userService, ExamService examService, QuestionService questionService,  ScoreService scoreService) {
        this.examService = examService;
        this.questionService = questionService;
        this.userService = userService;
        this.scoreService = scoreService;
    }

    //TODO : fix here
    public void correction(Map<String, String> answersMap) {
        Scores scores  ;
        Exam exam = examService.findExamById(Long.parseLong(answersMap.get("id")));
        User user = userService.findByUsername(answersMap.get("username"));
        List<Question> questions = questionService.findByExam(exam);

//        answersMap.remove("id");
//        answersMap.remove("username");
//
        for(int i = 0 ; i<=answersMap.size() ; i++){
            scores = new Scores();
            scores.setUser(user);
            scores.setExam(exam);
            scores.setQuestion(questions.get(i));
            scores.setAnswer(Long.parseLong(answersMap.get(questions.get(i).getId())));
            scoreService.save(scores);
        }

    }

    /*public Map<String,String> computeScore(User user, Exam exam) {
        Map<String, String> result = new HashMap<>();
        int grade;
        Scores scores = scoreService.findByExamIdAndUserId(exam.getId(), user.getId());
        List<Question> questions = questionService.findByExam(exam);
        questions.forEach(question -> {
                result.put(String.valueOf(question.getId()),scores.getAnswerId().get(question.getId()));
        });
        return result;
    }*/


}
