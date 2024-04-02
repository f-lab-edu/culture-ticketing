package com.culture.ticketing.user.api;

import com.culture.ticketing.user.application.UserService;
import com.culture.ticketing.user.application.dto.UserLoginRequest;
import com.culture.ticketing.user.application.dto.UserProfileResponse;
import com.culture.ticketing.user.application.dto.UserSaveRequest;
import com.culture.ticketing.user.domain.SecurityUser;
import com.culture.ticketing.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Api(tags = {"회원 API"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;

    public UserController(UserService userService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @ApiOperation(value = "회원 생성")
    @PostMapping
    public void postUser(@RequestBody UserSaveRequest request) {

        userService.createUser(request);
    }

    @ApiOperation(value = "회원 로그인")
    @PostMapping("/login")
    public void login(@RequestBody UserLoginRequest request, final HttpSession session) {

        Long userId = userService.login(request);

        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(userId));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        session.setMaxInactiveInterval(3600);
    }

    @ApiOperation(value = "회원 로그아웃")
    @PostMapping("/logout")
    public void logout(HttpSession session) {

        session.invalidate();
    }

    @ApiOperation(value = "회원 정보 조회")
    @GetMapping("/profile")
    public UserProfileResponse getUser(final Authentication authentication) {

        User user = ((SecurityUser) authentication.getPrincipal()).getUser();

        return UserProfileResponse.from(user);
    }
}
