package com.github.zmilad97.onlineExam.dbinit;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.module.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DbInit {
    private PasswordEncoder passwordEncoder;

    public DbInit(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


//    public Exam initExam() {
//        Exam exam = new Exam();
//        Question question1 = new Question();
//        Question question2 = new Question();
//        Question question3 = new Question();
//        Question question4 = new Question();
//        exam.setShowAnswer(false);
//        exam.setTime(10);
//        exam.setBeforeExamMessage("hi this is a test Exam");
//        exam.setMinusScore(false);
//        exam.setTitle("test exam");
//        exam.setCategory("test");
//        question1.setDescription("hi this is question 1");
//        Answer answer1 = new Answer();
//        answer1.setDescription("q1 answer 1");
//        answer1.setCorrect(true);
//        Answer answer2 = new Answer();
//        answer2.setDescription("q1 answer 2");
//        answer2.setCorrect(false);
//        Answer answer3 = new Answer();
//        answer3.setDescription("q1 answer 3");
//        answer3.setCorrect(false);
//        Answer answer4 = new Answer();
//        answer4.setDescription("q1 answer 4");
//        answer4.setCorrect(false);
//        answer1.setQuestionId(question1.getId());
//        answer2.setQuestionId(question1.getId());
//        answer3.setQuestionId(question1.getId());
//        answer4.setQuestionId(question1.getId());
//
//        question2.setDescription("hi this is question 2");
//        Answer answer21 = new Answer();
//        answer21.setDescription("q2 answer 1");
//        answer21.setCorrect(false);
//        Answer answer22 = new Answer();
//        answer22.setDescription("q2 answer 2");
//        answer22.setCorrect(true);
//        Answer answer23 = new Answer();
//        answer23.setDescription("q2 answer 3");
//        answer23.setCorrect(false);
//        Answer answer24 = new Answer();
//        answer24.setDescription("q2 answer 4");
//        answer24.setCorrect(false);
//        answer21.setQuestionId(question2.getId());
//        answer22.setQuestionId(question2.getId());
//        answer23.setQuestionId(question2.getId());
//        answer24.setQuestionId(question2.getId());
//
//
//        question3.setDescription("hi this is question 3");
//        Answer answer31 = new Answer();
//        answer31.setDescription("q3 answer 1");
//        answer31.setCorrect(false);
//        Answer answer32 = new Answer();
//        answer32.setDescription("q3 answer 2");
//        answer32.setCorrect(false);
//        Answer answer33 = new Answer();
//        answer33.setDescription("q3 answer 3");
//        answer33.setCorrect(true);
//        Answer answer34 = new Answer();
//        answer34.setDescription("q3 answer 4");
//        answer34.setCorrect(false);
//        answer31.setQuestionId(question3.getId());
//        answer32.setQuestionId(question3.getId());
//        answer33.setQuestionId(question3.getId());
//        answer34.setQuestionId(question3.getId());
//
//
//        question4.setDescription("hi this is question 4");
//        Answer answer41 = new Answer();
//        answer41.setDescription("q1 answer 1");
//        answer41.setCorrect(false);
//        Answer answer42 = new Answer();
//        answer42.setDescription("q1 answer 2");
//        answer42.setCorrect(false);
//        Answer answer43 = new Answer();
//        answer43.setDescription("q1 answer 3");
//        answer43.setCorrect(false);
//        Answer answer44 = new Answer();
//        answer44.setDescription("q1 answer 4");
//        answer44.setCorrect(true);
//
//        answer41.setQuestionId(question4.getId());
//        answer42.setQuestionId(question4.getId());
//        answer43.setQuestionId(question4.getId());
//        answer44.setQuestionId(question4.getId());
//
//        return exam;
//    }

    public User initUser() {
        User user = new User();
        user.setUsername("admin");
        user.setName("Milad Zaeri");
        user.setPassword(passwordEncoder.encode("milad"));
        user.setBirthDate("1997");
        user.setEmail("zmilad97@gmail.com");
        user.setGender(User.Gender.MALE);
        user.setPermissions("admin");
        user.setRoles("ADMIN");
        user.setActive(true);
        return user;
    }
}
