package com.github.zmilad97.onlineExam.controller;

import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("giverole/{userId}")
    public void giveRole(@PathVariable long userId, @RequestBody String role) {

        userService.findUserById(userId).setRoles(role);
    }

    //this method removes a permission from user
    @PostMapping("takepermission/{userId}")
    public void takePermission(@PathVariable long userId, @RequestBody String permission) {
        List<String> permissionList = userService.findUserById(userId).getPermissionList();
        permissionList.remove(permission);
        userService.findUserById(userId).setPermissions(permissionList);
    }

    @GetMapping("allusers")
    public List<User> getAllUsers(){
       return userService.findAll();
    }



}
