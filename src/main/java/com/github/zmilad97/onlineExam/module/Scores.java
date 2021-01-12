package com.github.zmilad97.onlineExam.module;


import javax.persistence.*;

@Entity
@IdClass(ScoresIdClass.class) //you can define a normal scoreId and just index the userId and questionId foreign keys to get rid of ScoresIdClass
public class Scores {

    @Id
    private long userId;
    @Id
    private long examId; //I think this relation here is redundant, as you have the QuestionId and the Question entity is related to the exam itself
    @Id
    private long questionId;

    private int answer;
  //it's useless to define empty default constructor when you don't have other constructors
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
