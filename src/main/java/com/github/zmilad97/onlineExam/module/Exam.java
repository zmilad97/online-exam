package com.github.zmilad97.onlineExam.module;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class Exam {
    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String category;
    private String grade;
    private int time;
    private boolean minusScore;
    private boolean showAnswer;
    private boolean showScore;
    private String beforeExamMessage;
    private long makerId;
    private boolean active;

    public Exam(){

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isMinusScore() {
        return minusScore;
    }

    public void setMinusScore(boolean minusScore) {
        this.minusScore = minusScore;
    }

    public boolean isShowAnswer() {
        return showAnswer;
    }

    public void setShowAnswer(boolean showAnswer) {
        this.showAnswer = showAnswer;
    }

    public boolean isShowScore() {
        return showScore;
    }

    public void setShowScore(boolean showScore) {
        this.showScore = showScore;
    }

    public String getBeforeExamMessage() {
        return beforeExamMessage;
    }

    public void setBeforeExamMessage(String beforeExamMessage) {
        this.beforeExamMessage = beforeExamMessage;
    }


    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getMakerId() {
        return makerId;
    }

    public void setMakerId(long makerId) {
        this.makerId = makerId;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //    public void addStudent(String id){
//        this.studentsId.add(id);
//    }
//
//    public List<String> getStudentsId() {
//        return studentsId;
//    }
//
//    public void setStudentsId(List<String> studentsId) {
//        this.studentsId = studentsId;
//    }
}
