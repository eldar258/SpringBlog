package com.example.blog.endpoint;

import com.example.blog.domain.User;
import com.example.blog.model.CreateUserRequest;
import com.example.blog.model.UpdateUserRequest;
import com.example.blog.model.UserDto;
import com.example.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity getAll(@PageableDefault(size = 5) Pageable pageable) {
        var page = userService.getAll(pageable);

        List<UserDto> userDtos = page.toList().stream()
                .map(this::userToUserDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<>(userDtos, pageable, page.getTotalElements()));
    }
//
//    @GetMapping("/get")
//    public ResponseEntity get(@RequestParam String id) {
//
//    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody CreateUserRequest request) {
        var user = userService.create(request.getLogin(), request.getEmail(), request.getPassword());

        var userDto = userToUserDto(user);

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/update")
    public ResponseEntity<UserDto> update(@RequestBody UpdateUserRequest request) {
        var user = userService.update(request.getId(), request.getEmail());

        var userDto = userToUserDto(user);

        return ResponseEntity.ok(userDto);
    }

    private UserDto userToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .build();
    }
}
