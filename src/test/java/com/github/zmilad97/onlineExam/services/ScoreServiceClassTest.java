package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.module.Scores;
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
class ScoreServiceClassTest {

    @Resource
    private QuestionService questionService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private ExamService examService;
    @Resource
    private UserService userService;

    @Test
    @Sql("classpath:test-data.sql")
    @DisplayName("this method calculate the score of the student by getting the examId")
    public void calculate_result() {

        List<Question> questions = questionService.findByExam(examService.findExamById(1));
        double score = 0;
        for (Question question : questions) {
            if (
                    scoreService.findByUserAndExamAndQuestion(userService.findUserById(1), examService.findExamById(1), question).getAnswer()
                            == questionService.findQuestionById(question.getId()).getCorrect())
                score++;
        }
        Assertions.assertThat(score).isEqualTo(1);
    }

    @Test
    @Sql("classpath:test-data.sql")
    @DisplayName("this method should save user's answers in a scores object")
    public void takeAnswers_fromUser_whenSave_OK() {
        Map<Long,Long> answers = new HashMap();
        answers.put((long) 1, (long) 1);
        for(Map.Entry<Long,Long> entry :answers.entrySet())
        {
            Scores scores = new Scores();
            scores.setExam(examService.findExamById(entry.getKey()));
            scores.setQuestion(questionService.findQuestionById(entry.getKey()));
            scores.setAnswer(entry.getValue());
            scores.setUser(userService.findUserById(3));

            if (!(scoreService.existsByUserAndExamAndQuestion(scores.getUser(), scores.getExam(), scores.getQuestion())))
                scoreService.save(scores);
        }
      Assertions.assertThat(scoreService.existsByUserAndExamAndQuestion(
              userService.findUserById(3),examService.findExamById(1),questionService.findQuestionById( 1))).isTrue();
    }

    @Test
    @Sql("classpath:test-data.sql")
    @DisplayName("this method should return user's answers in list of scores objects to the ADMIN or MASTER" )
    public void takeExamId_thenReturn_ScoresOfUser() {
        List<Scores> scores = new ArrayList<>();
        if (examService.findExamById(1).getMaker().equals(userService.findUserById(1))
                || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))
            scores = scoreService.findByExam(examService.findExamById(1));
        Assertions.assertThat(scores).isNotEmpty();
    }
}