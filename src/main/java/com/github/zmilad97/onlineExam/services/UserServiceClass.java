package com.github.zmilad97.onlineExam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceClass {
    private final UserService userService;
    private final ExamService examService;

    @Autowired
    public UserServiceClass(UserService userService, ExamService examService) {
        this.userService = userService;
        this.examService = examService;
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


}
