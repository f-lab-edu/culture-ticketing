package com.culture.ticketing.user.api;

import com.culture.ticketing.user.application.UserService;
import com.culture.ticketing.user.application.dto.UserLoginRequest;
import com.culture.ticketing.user.application.dto.UserSaveRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = {"회원 API"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "회원 생성")
    @PostMapping
    public void postUser(@RequestBody UserSaveRequest request) {

        userService.createUser(request);
    }

    @ApiOperation(value = "회원 로그인")
    @PostMapping("/login")
    public void login(@RequestBody UserLoginRequest request, HttpServletRequest httpRequest) {

        Long userId = userService.login(request);

        HttpSession session = httpRequest.getSession();
        session.setAttribute("userId", userId);
        session.setMaxInactiveInterval(3600);
    }

    @ApiOperation(value = "회원 로그아웃")
    @PostMapping("/logout")
    public void logout(HttpSession session) {

        session.invalidate();
    }
}
