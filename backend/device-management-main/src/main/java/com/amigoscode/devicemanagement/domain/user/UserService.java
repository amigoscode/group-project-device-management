package com.amigoscode.devicemanagement.domain.user;

import com.amigoscode.devicemanagement.domain.user.exception.UserNotFoundException;
import com.amigoscode.devicemanagement.domain.user.model.PageUser;
import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.domain.user.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EncodingService encoder;
    private final Clock clock;

    public User save(User user, String creatorId) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        ZonedDateTime createdAt = ZonedDateTime.now(clock);
        user.setCreatedAt(createdAt);
        user.setUpdatedBy(creatorId);
        return userRepository.save(
                user.withPassword(
                        encoder.encode(user.getPassword())
                )
        );
    }

    public void update(User user, String updaterId) {
        user.setUpdatedAt(ZonedDateTime.now(clock));
        user.setUpdatedBy(updaterId);
        userRepository.update(user);
    }

    public void removeById(String id) {
        userRepository.remove(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public PageUser findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}