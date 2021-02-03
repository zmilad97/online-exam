package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.module.Scores;
import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.repository.ExamRepository;
import com.github.zmilad97.onlineExam.repository.QuestionRepository;
import com.github.zmilad97.onlineExam.repository.ScoreRepository;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository, ExamRepository examRepository, QuestionRepository questionRepository) {
        this.scoreRepository = scoreRepository;
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
    }

    public double result(User user, Exam exam) {
        List<Question> questions = questionRepository.findByExam(exam);
        double score = 0;
        for (Question question : questions) {
            if (
                    scoreRepository.findByUserAndExamAndQuestion(user, exam, question).getAnswer()
                            == questionRepository.findQuestionById(question.getId()).getCorrect())
                score++;
        }
        return score;
    }

    public void takeAnswers(Map<Long,Long> answers,long examId) {
        for(Map.Entry<Long,Long> entry :answers.entrySet())
        {
            Scores scores = new Scores();
            scores.setExam(examRepository.findExamById(examId));
            scores.setQuestion(questionRepository.findQuestionById(entry.getKey()));
            scores.setAnswer(entry.getValue());
            scores.setUser(SecurityUtil.getCurrentUser());

            if (!(scoreRepository.existsByUserAndExamAndQuestion(scores.getUser(), scores.getExam(), scores.getQuestion())))
                scoreRepository.save(scores);

        }

    }

    /**
     * this method return List of user's scores of specific exam to the MASTER or ADMIn
     * @param examId send by MASTER or ADMIN
     * @return scores
     */

    public List<Scores> getUsersScores(long examId) {
        List<Scores> scores = new ArrayList<>();
        if (examRepository.findExamById(examId).getMaker().equals(SecurityUtil.getCurrentUser())
                || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))
            scores = scoreRepository.findByExam(examRepository.findExamById(examId));

        return scores;
    }


}
