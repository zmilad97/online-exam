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

    @PostMapping("give-role/{userId}")
    public void giveRole(@PathVariable long userId, @RequestBody String role) {
//        User user = userService.findUserById(userId);
//        user.setRoles(role);
//        userService.save(user);
        userService.findUserById(userId).setRoles(role); //it could be better to do it internally by userService
        userService.save(userService.findUserById(userId));
    }

    //this method removes a permission from user
    @PostMapping("take-permission/{userId}")
    public void takePermission(@PathVariable long userId, @RequestBody String permission) {
      //it could be better to do it internally in userService
        List<String> permissionList = userService.findUserById(userId).getPermissionList();
        permissionList.remove(permission);
        userService.findUserById(userId).setPermissions(permissionList);
        userService.save(userService.findUserById(userId));
    }

    @GetMapping("all-users")
    public List<User> getAllUsers(){
      //Do you need pagination here?
        return userService.findAll();
    }


    @GetMapping("find-user/{id}")
    public User findUserById(@PathVariable long id){
       return userService.findUserById(id);
    }

}
