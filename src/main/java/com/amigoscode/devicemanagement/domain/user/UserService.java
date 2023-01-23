package com.amigoscode.devicemanagement.domain.user;

import com.amigoscode.devicemanagement.domain.user.model.PageUser;
import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EncodingService encoder;

    public User save(User user) {
        return userRepository.save(
                user.withPassword(
                        encoder.encode(user.getPassword())
                )
        );
    }

    public void update(User user) {
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