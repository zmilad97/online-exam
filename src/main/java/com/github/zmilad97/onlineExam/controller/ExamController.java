package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import com.github.zmilad97.onlineExam.services.CorrectionService;
import com.github.zmilad97.onlineExam.services.ExamService;
import com.github.zmilad97.onlineExam.services.QuestionService;
import com.github.zmilad97.onlineExam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exam/")
public class ExamController {
    private final ExamService examService;
    private final CorrectionService correctionService;
    private final QuestionService questionService;
    private final UserService userService;

    @Autowired
    public ExamController(ExamService examService, CorrectionService correctionService, QuestionService questionService, UserService userService) {
        this.examService = examService;
        this.correctionService = correctionService;
        this.questionService = questionService;
        this.userService = userService;
    }

    //adding a exam
    @PostMapping("add")
    public void addExam(@RequestBody Exam exam) {
        exam.setMakerId(SecurityUtil.getCurrentUser().getId());
        exam.setActive(true);
        examService.save(exam);
    }

    //returns all creator's exams
    @GetMapping("myExam")
    public List<Exam> myExam() {
        return examService.findByMakerId(SecurityUtil.getCurrentUser().getId());
    }

    //returns all available exams
    @GetMapping("available")
    public List<Exam> available() {

        return examService.findByActiveTrue();
    }

    @GetMapping("{examId}")
    public String getExamTitleById(@PathVariable String examId) {
        return examService.findById(Long.parseLong(examId)).getTitle();
    }

    @GetMapping("/{examId}/questions")
    public List<Question> getQuestionsByExamId(@PathVariable String examId) {
        //TODO : need to check if student participate or not
        return questionService.findByExamId(examId);
    }


    //this method allows Master or Admin to choose students for taking exam
    @PostMapping("{examId}/add2exam")
    public void givePermissionToStudent(@RequestBody List<Long> studentsId, @PathVariable String examId) {

        if (examService.findById(Long.parseLong(examId)).getMakerId() == SecurityUtil.getCurrentUser().getId())
            studentsId.forEach(id -> userService.findUserById(id).addPermission(examId));
    }


    //this method gets the User's answers of a exam in a map structure that has a userId and a examId Key and value
    @PostMapping("answer")
    public void takeAnswers(@RequestBody Map<String, String> answer) {
        correctionService.correction(answer);
    }


    //this method return list of exam that in same category
    @GetMapping("category/{categoryName}")
    public List<Exam> getExamsByCategory(@PathVariable String categoryName) {
        return examService.findByCategory(categoryName);
    }

    //returns the exam in same grade
    @GetMapping("grade/{grade}")
    public List<Exam> getExamsByGrade(@PathVariable String grade) {
        return examService.findByGrade(grade);
    }

    //search the exam by title
    @GetMapping("search/{title}")
    public List<Exam> searchExam(@PathVariable String title) {
        return examService.findAllByTitleContains(title);
    }


}
