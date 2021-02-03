package com.github.zmilad97.onlineExam.repository;

import com.github.zmilad97.onlineExam.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findUserById(long id);
    void deleteById(long id);

}
