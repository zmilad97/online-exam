package com.github.zmilad97.onlineExam.starter;

import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class OnlineExamStarter implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public OnlineExamStarter(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * this method runs at service startup and checks
     * whether the database has the admin user or not
     * if not save admin user in database
     * id = 1 -----> admin
     */
    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findUserById(1) == null)
            userRepository.save(adminUser());
    }


    public User adminUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setName("admin admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setBirthdate("1997");
        user.setEmail("admin@gmail.com");
        user.setGender(User.Gender.MALE);
        user.setPermissions(Collections.singletonList("admin"));
        user.setRoles("ADMIN");
        user.setActive(true);
        return user;
    }
}
