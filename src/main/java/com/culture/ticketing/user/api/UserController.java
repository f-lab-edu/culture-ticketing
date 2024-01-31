package com.culture.ticketing.user.api;

import com.culture.ticketing.user.application.UserService;
import com.culture.ticketing.user.application.dto.UserSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void postUser(@RequestBody UserSaveRequest request) {

        userService.createUser(request);
    }
}
