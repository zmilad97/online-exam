package com.github.zmilad97.onlineExam.services;

import com.github.zmilad97.onlineExam.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findUserById(Long id);
    void deleteById(Long id);

}
