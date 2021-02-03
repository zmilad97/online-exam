package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.repository.ExamRepository;
import com.github.zmilad97.onlineExam.repository.QuestionRepository;
import com.github.zmilad97.onlineExam.repository.ScoreRepository;
import com.github.zmilad97.onlineExam.repository.UserRepository;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import com.github.zmilad97.onlineExam.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exam/")
public class ExamController {
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ScoreRepository scoreRepository;
    private final UserService userService;

    @Autowired
    public ExamController(ExamRepository examRepository, QuestionRepository questionRepository, UserRepository userRepository, ScoreRepository scoreRepository, UserService userService) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.scoreRepository = scoreRepository;
        this.userService = userService;
    }

    /**
     * this method add an exam to the database
     *
     * @param exam this param can be send by client whom has MASTER or ADMIN role
     */
    @PostMapping("add")
    public void addExam(@RequestBody Exam exam) {
        exam.setMaker(SecurityUtil.getCurrentUser());
        exam.setActive(true);
        examRepository.save(exam);
    }

    /**
     * this method returns a list of Exams that made by request sender
     *
     * @return List of Exam
     */
    @GetMapping("myExam")
    public List<Exam> myExam() {
        return examRepository.findByMaker(SecurityUtil.getCurrentUser());
    }

    /**
     * this method returns a list of exams that are active and the student has the permission for the student
     *
     * @return List of exams
     */
    @GetMapping("available")
    public List<Exam> availableExams() {
        return examRepository.findByActiveTrueAndIdIn(SecurityUtil.getCurrentUser().getPermissionList()
                .stream().map(Long::parseLong).collect(Collectors.toList()));
    }

    @GetMapping("{examId}")
    public Exam getExamById(@PathVariable long examId) {
        return examRepository.findExamById(examId);
    }

    @GetMapping("/{examId}/questions")
    public ResponseEntity<List<Question>> getQuestionsByExamId(@PathVariable long examId) {
        if (scoreRepository.findByUserAndExam(SecurityUtil.getCurrentUser(), examRepository.findExamById(examId)) == null)
            return ResponseEntity.ok(questionRepository.findByExam(examRepository.findExamById(examId)));
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * this method gives a permission to an exam that specified in path to list of student that sent by MASTER or ADMIN
     *
     * @param examId     it's the permission send by Master
     * @param studentsId list of students
     */
    @PostMapping("{examId}/add-to-exam")
    public void givePermissionToStudent(@RequestBody List<Long> studentsId, @PathVariable long examId) {

        if (examRepository.findExamById(examId).getMaker() == SecurityUtil.getCurrentUser()
                || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))
            userService.givePermission(studentsId, String.valueOf(examId));
    }

    /**
     * this method allows MASTER or ADMIN to remove a student from an exam
     *
     * @param examId    it's the permission send by Master
     * @param studentId student id
     */
    @PostMapping("{examId}/remove-from-exam")
    public void takePermission(@RequestBody Long studentId, @PathVariable long examId) {

        if (examRepository.findExamById(examId).getMaker() == SecurityUtil.getCurrentUser()
                || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))

            userService.takePermission(studentId, String.valueOf(examId));
    }




    /**
     * this method return list of exam that in same category
     *
     * @return List of exams
     */
    @GetMapping("category/{categoryName}")
    public List<Exam> getExamsByCategory(@PathVariable String categoryName) {
        return examRepository.findByCategoryAndMaker(categoryName,SecurityUtil.getCurrentUser());
    }


    /**
     * returns the exam in same grade
     *
     * @return List of exams
     */
    @GetMapping("grade/{grade}")
    public List<Exam> getExamsByGrade(@PathVariable String grade) {
        return examRepository.findByGradeAndMaker(grade,SecurityUtil.getCurrentUser());
    }

    /**
     * search the exam by title
     *
     * @return List of exams
     */
    @GetMapping("search/{title}")
    public List<Exam> searchExam(@PathVariable String title) {
        return examRepository.findAllByTitleContainsAndMaker(title,SecurityUtil.getCurrentUser());
    }

}
