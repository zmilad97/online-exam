package com.github.zmilad97.onlineExam.module;


import javax.persistence.*;

@Entity
public class Scores {

    @Id
    @GeneratedValue
    private long Id;

    @OneToOne
    private User user;

    @OneToOne
    private Exam exam;

    @OneToOne
    private Question question;

    private long answer;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setAnswer(long answer) {
        this.answer = answer;
    }

    public long getAnswer() {
        return answer;
    }

    public void setAnswer(Long answerId) {
        this.answer = answerId;
    }

}
