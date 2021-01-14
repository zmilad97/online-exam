package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.module.Scores;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceClass {
    private final ScoreService scoreService;
    private final ExamService examService;
    private final QuestionService questionService;

    @Autowired
    public ScoreServiceClass(ScoreService scoreService, ExamService examService, QuestionService questionService) {
        this.scoreService = scoreService;
        this.examService = examService;
        this.questionService = questionService;
    }

    public double result(long userId, long examId) {
        List<Question> questions = questionService.findByExamId(examId);
        double score = 0;
        for (Question question : questions) {if (
            scoreService.findByUserIdAndExamIdAndQuestionId(userId, examId, question.getId()).getAnswer()
               ==   questionService.findQuestionById(question.getId()).getCorrect())
           score++;
        }
        return score;
    }

    public void takeAnswers(List<Long> answers){
        for (int i = 1; i < answers.size(); i++) {
            Scores scores = new Scores();
            scores.setExamId(answers.get(0));
            scores.setQuestionId(questionService.findByExamId(answers.get(0)).get(i - 1).getId());
            scores.setAnswer(answers.get(i));
            scores.setUserId(SecurityUtil.getCurrentUser().getId());

            if (!(scoreService.existsByUserIdAndExamIdAndQuestionId(scores.getUserId(), scores.getExamId(), scores.getQuestionId())))
                scoreService.save(scores);
        }
    }
}
