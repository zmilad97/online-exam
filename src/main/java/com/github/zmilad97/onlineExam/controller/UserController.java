package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import com.github.zmilad97.onlineExam.services.UserService;
import com.github.zmilad97.onlineExam.services.UserServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserController {
    private final UserService userService;
    private final UserServiceClass userServiceClass;

    @Autowired
    public UserController(UserService userService, UserServiceClass userServiceClass) {

        this.userService = userService;
        this.userServiceClass = userServiceClass;
    }

    @PostMapping("add")
    public void addUser(@RequestBody User user) {
        userServiceClass.addUser(user);
    }

    @GetMapping("names")
    public Map<Long, String> getUsers() {
        Map<Long, String> users = new HashMap<>();
        userService.findAll().forEach(user -> users.put(user.getId(), user.getName()));
        return users;
    }


    @DeleteMapping("remove/{id}")
    public void removeUserById(@PathVariable long id) {
        userService.deleteById(id);
    }

    @GetMapping("status")
    public User myStatus() {
        return SecurityUtil.getCurrentUser();
    }


}
