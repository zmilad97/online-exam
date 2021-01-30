package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.services.ExamService;
import com.github.zmilad97.onlineExam.services.UserService;
import com.github.zmilad97.onlineExam.services.UserServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/")
public class AdminController {
    private final UserService userService;
    private final UserServiceClass userServiceClass;
    private final ExamService examService;

    @Autowired
    public AdminController(UserService userService, UserServiceClass userServiceClass, ExamService examService) {
        this.userService = userService;
        this.userServiceClass = userServiceClass;
        this.examService = examService;
    }

    /**
     * this method take role and give it to user that specified by userId
     *
     * @param userId the user id given in path
     * @param role   the role in String send by client
     */
    @PostMapping("give-role/{userId}")
    public void giveRole(@PathVariable long userId, @RequestBody String role) {
        userServiceClass.setRole(userId, role);
    }

    /**
     * this method take permission and take it from user that specified by userId
     *
     * @param userId     the user id given in path
     * @param permission the permission in String send by client
     */
    @PostMapping("take-permission/{userId}")
    public void takePermission(@PathVariable long userId, @RequestBody String permission) {
        userServiceClass.takePermission(userId, permission);
    }

    @GetMapping("all-users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    /**
     * this method finds a user by given id
     *
     * @param userId the user id given in path
     * @return User
     */
    @GetMapping("find-user/{userId}")
    public User findUserById(@PathVariable String userId) {
        return userService.findUserById(Long.valueOf(userId));
    }

    /**
     *
     * Exam Search methods
     */

    @GetMapping("exam/all")
    public List<Exam> allExam() {
        return examService.findAll();
    }

    @GetMapping("exam/grade/{grade}")
    public List<Exam> getExamByGrade(@PathVariable String grade){
        return examService.findByGrade(grade);
    }

    @GetMapping("exam/category/{category}")
    public List<Exam> getExamByCategory(@PathVariable String category){
        return examService.findByCategory(category);
    }

    @GetMapping("exam/title/{title}")
    public List<Exam> getExamByTitle(@PathVariable String title){
        return examService.findByGrade(title);
    }

    @GetMapping("exam/maker/{makerId}")
    public List<Exam> getExamByMaker(@PathVariable Long makerId){
        return examService.findByMaker(userService.findUserById(makerId));
    }





}
