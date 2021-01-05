package com.github.zmilad97.onlineExam.module;


import javax.persistence.*;

@Entity
@IdClass(ScoresIdClass.class)
public class Scores {

    @Id
    private long userId;
    @Id
    private long examId;
    @Id
    private long questionId;

    private int answer;

    public Scores() {

    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answerId) {
        this.answer = answerId;
    }

}
