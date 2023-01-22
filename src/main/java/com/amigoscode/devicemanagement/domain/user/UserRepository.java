package com.amigoscode.devicemanagement.domain.user;

import com.amigoscode.devicemanagement.domain.user.model.PageUser;
import com.amigoscode.devicemanagement.domain.user.model.User;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    void update(User user);

    void remove(String id);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    PageUser findAll(Pageable pageable);

}