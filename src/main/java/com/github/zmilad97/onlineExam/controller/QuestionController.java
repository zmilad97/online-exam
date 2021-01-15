package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.services.ExamService;
import com.github.zmilad97.onlineExam.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final ExamService examService;
    @Autowired
    public QuestionController(QuestionService questionService, ExamService examService) {
        this.questionService = questionService;
        this.examService = examService;
    }

   @GetMapping("/append")
   public String append(){
        return "/addQuestion.html";
   }

    @PostMapping("/add/{examId}")
    public void add(@RequestBody Question question, @PathVariable Long examId){
        question.setExam(examService.findExamById(examId));
        questionService.save(question);
    }




}
