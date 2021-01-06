package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.dbinit.DbInit;
import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("add")
    public void addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userService.save(user);
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


    @GetMapping("init")
    public String init() {
        DbInit dbInit = new DbInit(passwordEncoder);
        User user = dbInit.initUser();
        userService.save(user);
        return "init User Done";
    }

}
