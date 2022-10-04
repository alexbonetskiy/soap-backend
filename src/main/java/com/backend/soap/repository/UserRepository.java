package com.backend.soap.repository;

import com.backend.soap.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, String> {

    @Modifying
    @Query("DELETE FROM User u WHERE u.login=:login")
    int delete(String login);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.login=:login")
    Optional<User> findByLoginWithRoles(String login);

    User findByLogin(String login);
}
