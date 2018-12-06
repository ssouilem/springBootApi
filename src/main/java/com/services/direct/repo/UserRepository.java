package com.services.direct.repo;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.services.direct.bean.security.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u from User u " +
            " where u.username = ?1")
    Optional<User> findUserWithName(String username);
    
    @Query("SELECT DISTINCT user FROM User user " +
            "INNER JOIN FETCH user.authorities AS authorities " +
            "LEFT JOIN FETCH user.company AS company " +
            "WHERE user.username = :username")
    User findByUsername(@Param("username") String username);
    
    boolean existsByUsername(String username);
    
    @Transactional
    void deleteByUsername(String username);
    

}
