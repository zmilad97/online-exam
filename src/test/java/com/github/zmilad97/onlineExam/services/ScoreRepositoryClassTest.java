package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.module.Scores;
import com.github.zmilad97.onlineExam.repository.ExamRepository;
import com.github.zmilad97.onlineExam.repository.QuestionRepository;
import com.github.zmilad97.onlineExam.repository.ScoreRepository;
import com.github.zmilad97.onlineExam.repository.UserRepository;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DataJpaTest
class ScoreRepositoryClassTest {

    @Resource
    private QuestionRepository questionRepository;
    @Resource
    private ScoreRepository scoreRepository;
    @Resource
    private ExamRepository examRepository;
    @Resource
    private UserRepository userRepository;

    @Test
    @Sql("classpath:test-data.sql")
    @DisplayName("this method calculate the score of the student by getting the examId")
    public void calculate_result() {

        List<Question> questions = questionRepository.findByExam(examRepository.findExamById(1));
        double score = 0;
        for (Question question : questions) {
            if (
                    scoreRepository.findByUserAndExamAndQuestion(userRepository.findUserById(1), examRepository.findExamById(1), question).getAnswer()
                            == questionRepository.findQuestionById(question.getId()).getCorrect())
                score++;
        }
        Assertions.assertThat(score).isEqualTo(1);
    }

    @Test
    @Sql("classpath:test-data.sql")
    @DisplayName("this method should save user's answers in a scores object")
    public void takeAnswers_fromUser_whenSave_OK() {
        Map<Long,Long> answers = new HashMap<>();
        answers.put((long) 1, (long) 1);
        for(Map.Entry<Long,Long> entry :answers.entrySet())
        {
            Scores scores = new Scores();
            scores.setExam(examRepository.findExamById(entry.getKey()));
            scores.setQuestion(questionRepository.findQuestionById(entry.getKey()));
            scores.setAnswer(entry.getValue());
            scores.setUser(userRepository.findUserById(3));

            if (!(scoreRepository.existsByUserAndExamAndQuestion(scores.getUser(), scores.getExam(), scores.getQuestion())))
                scoreRepository.save(scores);
        }
      Assertions.assertThat(scoreRepository.existsByUserAndExamAndQuestion(
              userRepository.findUserById(3), examRepository.findExamById(1), questionRepository.findQuestionById( 1))).isTrue();
    }

    @Test
    @Sql("classpath:test-data.sql")
    @DisplayName("this method should return user's answers in list of scores objects to the ADMIN or MASTER" )
    public void takeExamId_thenReturn_ScoresOfUser() {
        List<Scores> scores = new ArrayList<>();
        if (examRepository.findExamById(1).getMaker().equals(userRepository.findUserById(1))
                || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))
            scores = scoreRepository.findByExam(examRepository.findExamById(1));
        Assertions.assertThat(scores).isNotEmpty();
    }
}