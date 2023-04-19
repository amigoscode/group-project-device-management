package com.amigoscode.devicemanagement.domain.user;

import com.amigoscode.devicemanagement.domain.user.exception.UserAlreadyExistsException;
import com.amigoscode.devicemanagement.domain.user.exception.UserNotFoundException;
import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.domain.user.model.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EncodingService encoder;

    @Mock
    private Clock clock;

    @InjectMocks
    private UserService userService;

    private final User fakeUser = new User(
            "ID28",
            "email@email.any",
            "user name",
            "pass",
            Set.of(UserRole.DEVICE_OWNER),
            ZonedDateTime.of(2023, 4, 17, 12, 30, 20, 0, ZoneId.of("UTC")),
            ZonedDateTime.of(2023, 4, 17, 12, 30, 20, 0, ZoneId.of("UTC")),
            ZonedDateTime.of(2023, 4, 17, 12, 30, 20, 0, ZoneId.of("UTC")),
            "updatedBy"
    );

    private static ZonedDateTime NOW = ZonedDateTime.of(
            2023,
            4,
            17,
            12,
            30,
            20,
            0,
            ZoneId.of("UTC")
    );

    @Test
    void update_method_should_not_throw_exception() {
        Mockito.when((clock.getZone())).thenReturn(NOW.getZone());
        Mockito.when((clock.instant())).thenReturn(NOW.toInstant());
        // Expect
        Assertions.assertDoesNotThrow(() -> userService.update(fakeUser, "updaterId"));
    }

    @Test
    void delete_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> userService.removeById(fakeUser.getId()));
    }

    @Test
    void save_method_should_return_saved_user_when_user_does_not_exist() {
        Mockito.when((clock.getZone())).thenReturn(NOW.getZone());
        Mockito.when((clock.instant())).thenReturn(NOW.toInstant());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(fakeUser);

        //when
        User savedUser = userService.save(fakeUser, "creatorId");

        //then
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(fakeUser.getId(), savedUser.getId());
        Assertions.assertEquals(fakeUser.getEmail(), savedUser.getEmail());
        Assertions.assertEquals(fakeUser.getName(), savedUser.getName());
        Assertions.assertEquals(fakeUser.getPassword(), savedUser.getPassword());
    }

    @Test
    void save_method_should_throw_user_already_exist_exception_when_user_exist() {
        Mockito.when((clock.getZone())).thenReturn(NOW.getZone());
        Mockito.when((clock.instant())).thenReturn(NOW.toInstant());
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenThrow(new UserAlreadyExistsException());

        //when
        //then
        Assertions.assertThrows(UserAlreadyExistsException.class,
                ()-> userService.save(fakeUser, "creatorId"));
    }

    @Test
    void find_by_email_method_should_return_founded_user_when_user_exist() {
        Mockito.when(userRepository.findByEmail(fakeUser.getEmail())).thenReturn(Optional.of(fakeUser));

        //when
        User foundedUser = userService.findByEmail(fakeUser.getEmail());

        //then
        Assertions.assertNotNull(foundedUser);
        Assertions.assertEquals(fakeUser.getId(), foundedUser.getId());
        Assertions.assertEquals(fakeUser.getEmail(), foundedUser.getEmail());
        Assertions.assertEquals(fakeUser.getName(), foundedUser.getName());
        Assertions.assertEquals(fakeUser.getPassword(), foundedUser.getPassword());
    }

    @Test
    void find_by_email_method_should_throw_user_not_found_exception_when_user_does_not_exist() {
        Mockito.when(userRepository.findByEmail(fakeUser.getEmail())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(UserNotFoundException.class,
                ()-> userService.findByEmail(fakeUser.getEmail()));
    }

    @Test
    void find_by_id_method_should_return_founded_user_when_user_exist() {
        Mockito.when(userRepository.findById(fakeUser.getId())).thenReturn(Optional.of(fakeUser));

        //when
        User foundedUser = userService.findById(fakeUser.getId());

        //then
        Assertions.assertNotNull(foundedUser);
        Assertions.assertEquals(fakeUser.getId(), foundedUser.getId());
        Assertions.assertEquals(fakeUser.getEmail(), foundedUser.getEmail());
        Assertions.assertEquals(fakeUser.getName(), foundedUser.getName());
        Assertions.assertEquals(fakeUser.getPassword(), foundedUser.getPassword());
    }

    @Test
    void find_by_id_method_should_throw_user_not_found_exception_when_user_does_not_exist() {
        Mockito.when(userRepository.findById(fakeUser.getId())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(UserNotFoundException.class,
                ()-> userService.findById(fakeUser.getId()));
    }

}