package com.amigoscode.devicemanagement.external.storage.user;

import com.amigoscode.devicemanagement.domain.user.UserRepository;
import com.amigoscode.devicemanagement.domain.user.model.PageUser;
import com.amigoscode.devicemanagement.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
@Component
class UserStorageAdapter implements UserRepository {

    private final MongoUserRepository userRepository;
    private final UserEntityMapper mapper;

    @Override
    public User save(User user) {
        try {
            UserEntity saved = userRepository.insert(mapper.toEntity(user));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        } catch (DuplicateKeyException ex) {
            log.warning("User " + user.getEmail() + " already exits in db");
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public void update(User user) {
        userRepository.findById(user.getId()).ifPresent(userEntity -> userRepository.save(mapper.toEntity(user)));
    }

    @Override
    public void remove(String id) {
        userRepository.findById(id).ifPresent(userEntity -> userRepository.deleteById(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageUser findAll(Pageable pageable) {
        Page<UserEntity> pageOfUsersEntity = userRepository.findAll(pageable);
        List<User> usersOnCurrentPage = pageOfUsersEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageUser(
                usersOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfUsersEntity.getTotalPages(),
                pageOfUsersEntity.getTotalElements()
        );
    }
}
