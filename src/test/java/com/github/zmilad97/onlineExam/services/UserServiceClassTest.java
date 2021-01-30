package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest

class UserServiceClassTest {

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @TestConfiguration

    public static class testConfig {
        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }


    @Test
    @DisplayName("This method should save the given User to the DataBase")
    public void givenUser_whenSave_thenGetOk() {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setName("Milad Zaeri");
        user.setPassword(passwordEncoder.encode("milad"));
        user.setBirthdate("1997");
        user.setEmail("zmilad97@gmail.com");
        user.setGender(User.Gender.MALE);
        user.addPermission("admin");
        user.addPermission("user");
        user.addPermission("master");
        user.setRoles("ADMIN");
        user.setActive(true);
        userService.save(user);
        User savedUser = userService.findByUsername("admin");
        assertThat(savedUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(user);
    }

    @Test
    @Sql("classpath:test-data.sql")
    @DisplayName("this method should set a role to a saved user ")
    public void givenUserId_andRole_whenSetToTheUser_thenGetOk() {
        User user = userService.findUserById( 1);
        user.setRoles("User");
        userService.save(user);
        assertThat(userService.findUserById( 1).getRoles().equals("User"));
    }

    @Test
    @Sql("classpath:test-data.sql")
    @DisplayName("this method remove specific permission from user")
    public void givenUserId_andPermission_whenRemoveFromUser_thenGetOk() {
        String permission = "user";
        List<String> permissionList = new ArrayList<>(userService.findUserById( 1).getPermissionList());
        permissionList.remove(permission);
        userService.findUserById(1).setPermissions(permissionList);
        userService.save(userService.findUserById( 1));
        assertThat(userService.findUserById( 1).getPermissionList().contains("user")).isFalse();
    }


    @Test
    @Sql("classpath:test-data.sql")
    @DisplayName("this method get a list of student id and add a permission(exam) to their permission list")
    public void givenUserIdList_andPermission_whenAdded_thenOk() {
        List<Long> studentsId = Collections.singletonList((long) 1);
        String permission = "test";
        studentsId.forEach(id -> userService.findUserById(id).addPermission(permission));
        studentsId.forEach(id -> userService.save(userService.findUserById(id)));
        for(int i = 0 ; i < studentsId.size() ; i++)
        assertThat(userService.findUserById(studentsId.get(i)).getPermissionList()).contains("test") ;
    }


    @Test
    @Sql("classpath:test-data.sql")
    public void testsql(){
        User test = userService.findByUsername("admin");
        assertThat(test).isNotNull();
    }
}