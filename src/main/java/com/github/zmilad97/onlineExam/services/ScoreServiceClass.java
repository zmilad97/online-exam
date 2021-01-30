package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.module.Scores;
import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public double result(User user, Exam exam) {
        List<Question> questions = questionService.findByExam(exam);
        double score = 0;
        for (Question question : questions) {
            if (
                    scoreService.findByUserAndExamAndQuestion(user, exam, question).getAnswer()
                            == questionService.findQuestionById(question.getId()).getCorrect())
                score++;
        }
        return score;
    }

    public void takeAnswers(Map<Long,Long> answers,long examId) {
        for(Map.Entry<Long,Long> entry :answers.entrySet())
        {
            Scores scores = new Scores();
            scores.setExam(examService.findExamById(examId));
            scores.setQuestion(questionService.findQuestionById(entry.getKey()));
            scores.setAnswer(entry.getValue());
            scores.setUser(SecurityUtil.getCurrentUser());

            if (!(scoreService.existsByUserAndExamAndQuestion(scores.getUser(), scores.getExam(), scores.getQuestion())))
                scoreService.save(scores);

        }

    }

    /**
     * this method return List of user's scores of specific exam to the MASTER or ADMIn
     * @param examId send by MASTER or ADMIN
     * @return scores
     */

    public List<Scores> getUsersScores(long examId) {
        List<Scores> scores = new ArrayList<>();
        if (examService.findExamById(examId).getMaker().equals(SecurityUtil.getCurrentUser())
                || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))
            scores = scoreService.findByExam(examService.findExamById(examId));

        return scores;
    }


}
