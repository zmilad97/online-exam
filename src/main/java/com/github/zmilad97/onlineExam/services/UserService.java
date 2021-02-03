package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.repository.UserRepository;
import com.github.zmilad97.onlineExam.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user){
        if(!(userRepository.existsById(user.getId()))){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setPermissions(new ArrayList<>());
        user.addPermission("user");
        user.setRoles("USER");
        userRepository.save(user);
        }
    }

    public void setRole(long userId, String role) {
        userRepository.findUserById(userId).setRoles(role);
        userRepository.save(userRepository.findUserById(userId));
    }

    public void takePermission(long userId, String permission) {
        List<String> permissionList = userRepository.findUserById(userId).getPermissionList();
        permissionList.remove(permission);
        userRepository.findUserById(userId).setPermissions(permissionList);
        userRepository.save(userRepository.findUserById(userId));
    }

    public void givePermission(List<Long> studentsId, String examId) {
        List<User> users = userRepository.findAllById(studentsId);
        users.forEach(user -> user.addPermission(examId));
        userRepository.saveAll(users);
    }

    public void updateUser(User user){
        if(SecurityUtil.getCurrentUser().getId() == user.getId() || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN"))
            userRepository.save(user);
    }


}
