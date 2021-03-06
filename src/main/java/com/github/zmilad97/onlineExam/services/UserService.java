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

    public void addUser(User user) {
        if (!(userRepository.existsById(user.getId()))) {
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

    public void updateUser(User user) {
        if (SecurityUtil.getCurrentUser().getId() == user.getId() || SecurityUtil.getCurrentUser().getRoles().equals("ADMIN")) {
            User currentUser = SecurityUtil.getCurrentUser();
            if (!(user.getPassword().isEmpty()))
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getPassword()==null ||user.getPassword().isEmpty())
                user.setPassword(currentUser.getPassword());
            if (user.getName()==null||user.getName().isEmpty())
                user.setName(currentUser.getName());
            if (user.getBirthdate() == null||user.getBirthdate().isEmpty())
                user.setBirthdate(currentUser.getBirthdate());
            if (user.getEmail()==null||user.getEmail().isEmpty())
                user.setEmail(currentUser.getEmail());
            if (user.getPermissions()==null||user.getPermissionList().isEmpty())
                user.setPermissions(currentUser.getPermissionList());
            if (user.getRoles()==null||user.getRoles().isEmpty())
                user.setRoles(currentUser.getRoles());
            user.setUsername(currentUser.getUsername());
            user.setId(currentUser.getId());
            userRepository.save(user);
        }
    }


}
