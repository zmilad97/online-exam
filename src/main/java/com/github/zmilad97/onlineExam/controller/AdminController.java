package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.repository.ExamRepository;
import com.github.zmilad97.onlineExam.repository.UserRepository;
import com.github.zmilad97.onlineExam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/")
public class AdminController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ExamRepository examRepository;

    @Autowired
    public AdminController(UserRepository userRepository, UserService userService, ExamRepository examRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.examRepository = examRepository;
    }

    /**
     * this method take role and give it to user that specified by userId
     *
     * @param userId the user id given in path
     * @param role   the role in String send by client
     */
    @PostMapping("give-role/{userId}")
    public void giveRole(@PathVariable long userId, @RequestBody String role) {
        userService.setRole(userId, role);
    }

    /**
     * this method take permission and take it from user that specified by userId
     *
     * @param userId     the user id given in path
     * @param permission the permission in String send by client
     */
    @PostMapping("take-permission/{userId}")
    public void takePermission(@PathVariable long userId, @RequestBody String permission) {
        userService.takePermission(userId, permission);
    }

    @GetMapping("all-users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * this method finds a user by given id
     *
     * @param userId the user id given in path
     * @return User
     */
    @GetMapping("find-user/{userId}")
    public User findUserById(@PathVariable long userId) {
        return userRepository.findUserById(userId);
    }

    /**
     *
     * Exam Search methods
     */

    @GetMapping("exam/all")
    public List<Exam> allExam() {
        return examRepository.findAll();
    }

    @GetMapping("exam/grade/{grade}")
    public List<Exam> getExamByGrade(@PathVariable String grade){
        return examRepository.findByGrade(grade);
    }

    @GetMapping("exam/category/{category}")
    public List<Exam> getExamByCategory(@PathVariable String category){
        return examRepository.findByCategory(category);
    }

    @GetMapping("exam/title/{title}")
    public List<Exam> getExamByTitle(@PathVariable String title){
        return examRepository.findByGrade(title);
    }

    @GetMapping("exam/maker/{makerId}")
    public List<Exam> getExamByMaker(@PathVariable Long makerId){
        return examRepository.findByMaker(userRepository.findUserById(makerId));
    }





}
