package com.github.zmilad97.onlineExam.starter;

import com.github.zmilad97.onlineExam.module.User;
import com.github.zmilad97.onlineExam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class OnlineExamStarter implements ApplicationRunner {

    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public OnlineExamStarter(UserService userService) {
        this.userService = userService;
    }

    /**
     * this method runs at service startup and checks
     * whether the database has the admin user or not
     * if not save admin user in database
     * id = 0 -----> admin
     */
    @Override
    public void run(ApplicationArguments args) {
        if (userService.findUserById(0) == null)
            userService.save(adminUser());
    }


    public User adminUser() {
        User user = new User();
        user.setId(0);
        user.setUsername("admin");
        user.setName("Milad Zaeri");
        user.setPassword(passwordEncoder.encode("milad"));
        user.setBirthdate("1997");
        user.setEmail("zmilad97@gmail.com");
        user.setGender(User.Gender.MALE);
        user.addPermission("admin");
        user.setRoles("ADMIN");
        user.setActive(true);
        return user;
    }
}
