package com.culture.ticketing.user.api;

import com.culture.ticketing.user.application.UserService;
import com.culture.ticketing.user.application.dto.UserSaveRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"회원 Controller"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "회원 생성 API")
    @PostMapping
    public void postUser(@RequestBody UserSaveRequest request) {

        userService.createUser(request);
    }
}
