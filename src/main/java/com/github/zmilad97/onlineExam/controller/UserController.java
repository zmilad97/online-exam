package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import com.github.zmilad97.onlineExam.repository.UserRepository;
import com.github.zmilad97.onlineExam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {

        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("add")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @GetMapping("names")
    public Map<Long, String> getUsers() {
        Map<Long, String> users = new HashMap<>();
        userRepository.findAll().forEach(user -> users.put(user.getId(), user.getName()));
        return users;
    }

    @PostMapping("update")
    public void update(@RequestBody User user){
        userService.updateUser(user);
    }

    @DeleteMapping("remove/{id}")
    public void removeUserById(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("status")
    public User myStatus() {
        return SecurityUtil.getCurrentUser();
    }


}
