package com.github.zmilad97.onlineExam.dbinit;

import com.github.zmilad97.onlineExam.module.Exam;
import com.github.zmilad97.onlineExam.module.Question;
import com.github.zmilad97.onlineExam.module.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DbInit {
    static PasswordEncoder passwordEncoder;

    public static User initUser() {
        User user = new User();
        user.setUsername("admin");
        user.setName("Milad Zaeri");
        user.setPassword(passwordEncoder.encode("milad"));
        user.setBirthDate("1997");
        user.setEmail("zmilad97@gmail.com");
        user.setGender(User.Gender.MALE);
        user.addPermission("admin");
        user.setRoles("ADMIN");
        user.setActive(true);
        return user;
    }
}
