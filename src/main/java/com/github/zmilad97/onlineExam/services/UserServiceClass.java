package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceClass {
    private final UserService userService;
    private final ExamService examService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceClass(UserService userService, ExamService examService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.examService = examService;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user){
        if(!(userService.existsById(user.getId()))){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setPermissions(new ArrayList<>());
        user.addPermission("user");
        user.setRoles("USER");
        userService.save(user);
        }
    }

    public void setRole(long userId, String role) {
        userService.findUserById(userId).setRoles(role);
        userService.save(userService.findUserById(userId));
    }

    public void takePermission(long userId, String permission) {
        List<String> permissionList = userService.findUserById(userId).getPermissionList();
        permissionList.remove(permission);
        userService.findUserById(userId).setPermissions(permissionList);
        userService.save(userService.findUserById(userId));
    }

    public void givePermission(List<Long> studentsId, String examId) {
        studentsId.forEach(id -> userService.findUserById(id).addPermission(examId));
        studentsId.forEach(id -> userService.save(userService.findUserById(id)));
    }

    public void updateUser(User user){
        if(SecurityUtil.getCurrentUser().getId() == user.getId() || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))
            userService.save(user);
    }


}
