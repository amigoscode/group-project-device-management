package com.amigoscode.devicemanagement.api.user;

import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.security.Security;
import com.amigoscode.devicemanagement.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/users",
        produces = "application/json",
        consumes = "application/json"
)
class UserController {

    private final UserService userService;
    private final UserDtoMapper userMapper;
    private final PageUserDtoMapper pageUserDtoMapper;
    private final Security security;

    @GetMapping( path = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        User user = userService.findById(id);
        return ResponseEntity
                .ok(userMapper.toDto(user));
    }

    @GetMapping
    public ResponseEntity<PageUserDto> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageUserDto pageUsers = pageUserDtoMapper.toPageDto(userService.findAll(pageable));

        return ResponseEntity.ok(pageUsers);
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        User user = userService.save(userMapper.toDomain(dto), authenticatedUser.getId());
        return ResponseEntity
            .ok(userMapper.toDto(user));
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody UserDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        userService.update(userMapper.toDomain(dto), authenticatedUser.getId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeUser(@PathVariable String id){
        userService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("me")
    public ResponseEntity<UserDto> aboutMe() {
        User user = security.getPrincipal();

        return ResponseEntity
            .ok(userMapper.toDto(user));
    }
}
