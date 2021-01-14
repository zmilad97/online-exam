package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import com.github.zmilad97.onlineExam.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exam/")
public class ExamController {
    private final ExamService examService;
    private final CorrectionService correctionService;
    private final QuestionService questionService;
    private final UserService userService;
    private final ScoreService scoreService;
    private final UserServiceClass userServiceClass;

    @Autowired
    public ExamController(ExamService examService, CorrectionService correctionService, QuestionService questionService, UserService userService, ScoreService scoreService, UserServiceClass userServiceClass) {
        this.examService = examService;
        this.correctionService = correctionService;
        this.questionService = questionService;
        this.userService = userService;
        this.scoreService = scoreService;
        this.userServiceClass = userServiceClass;
    }

    /**
     * this method add an exam to the database
     *
     * @param exam this param can be send by client whom has MASTER or ADMIN role
     */
    @PostMapping("add")
    public void addExam(@RequestBody Exam exam) {
        exam.setMakerId(SecurityUtil.getCurrentUser().getId());
        exam.setActive(true);
        examService.save(exam);
    }

    /**
     * this method returns a list of Exams that made by request sender
     *
     * @return List of Exam
     */
    @GetMapping("myExam")
    public List<Exam> myExam() {
        return examService.findByMakerId(SecurityUtil.getCurrentUser().getId());
    }

    /**
     * this method returns a list of exams that are active and the student has the permission for the student
     *
     * @return List of exams
     */
    @GetMapping("available")
    public List<Exam> availableExams() {
        return examService.findByActiveTrueAndIdIn(SecurityUtil.getCurrentUser().getPermissionList()
                .stream().map(Long::parseLong).collect(Collectors.toList()));
    }

    @GetMapping("{examId}")
    public String getExamTitleById(@PathVariable long examId) {
        return examService.findById(examId).getTitle();
    }

    @GetMapping("/{examId}/questions")
    public List<Question> getQuestionsByExamId(@PathVariable long examId) {
        //TODO : need to check if student participate or not
        if (scoreService.findByUserIdAndExamId(SecurityUtil.getCurrentUser().getId(), examId) == null)
            return questionService.findByExamId(examId);
        else
            return null;
    }


    /**
     * this method gives a permission to an exam that specified in path to list of student that sent by MASTER or ADMIN
     *
     * @param examId it's the permission send by Master
     * @param studentsId list of students
     */
    @PostMapping("{examId}/add-to-exam")
    public void givePermissionToStudent(@RequestBody List<Long> studentsId, @PathVariable long examId) {

        if (examService.findById(examId).getMakerId() == SecurityUtil.getCurrentUser().getId()
                || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))
            userServiceClass.givePermission(studentsId, String.valueOf(examId));
    }

    /**
     * this method allows MASTER or ADMIN to remove a student from an exam
     *
     * @param examId it's the permission send by Master
     * @param studentId student id
     */
    @PostMapping("{examId}/remove-from-exam")
    public void takePermission(@RequestBody Long studentId, @PathVariable long examId) {

        if (examService.findById(examId).getMakerId() == SecurityUtil.getCurrentUser().getId()
                || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))

            userServiceClass.takePermission(studentId, String.valueOf(examId));
    }


    //TODO : add a method to get exam and answer


    //TODO : this method seems not right
    //this method gets the User's answers of a exam in a map structure that has a userId and a examId Key and value
    @PostMapping("answer")
    public void takeAnswers(@RequestBody Map<String, String> answer) {
        correctionService.correction(answer);
    }


    /**
     * this method return list of exam that in same category
     *
     * @return List of exams
     */
    @GetMapping("category/{categoryName}")
    public List<Exam> getExamsByCategory(@PathVariable String categoryName) {
        return examService.findByCategory(categoryName);
    }


    /**
     * returns the exam in same grade
     *
     * @return List of exams
     */
    @GetMapping("grade/{grade}")
    public List<Exam> getExamsByGrade(@PathVariable String grade) {
        return examService.findByGrade(grade);
    }

    /**
     * search the exam by title
     *
     * @return List of exams
     */
    @GetMapping("search/{title}")
    public List<Exam> searchExam(@PathVariable String title) {
        return examService.findAllByTitleContains(title);
    }


}
