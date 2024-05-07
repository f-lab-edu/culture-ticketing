package com.culture.ticketing.user.api

import com.culture.ticketing.config.SecurityConfig
import com.culture.ticketing.user.UserFixtures
import com.culture.ticketing.user.application.CustomUserDetailsService
import com.culture.ticketing.user.application.UserService
import com.culture.ticketing.user.application.dto.UserLoginRequest
import com.culture.ticketing.user.application.dto.UserSaveRequest
import com.culture.ticketing.user.domain.Role
import com.culture.ticketing.user.domain.SecurityUser
import com.culture.ticketing.user.domain.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UserController.class)
@AutoConfigureDataRedis
@Import(SecurityConfig.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpringBean
    private UserService userService = Mock();
    @SpringBean
    private CustomUserDetailsService userDetailsService = Mock();
    @SpringBean
    private PasswordEncoder passwordEncoder = Mock();
    @SpringBean
    private MockHttpSession session = Mock();

    def "유저 생성 성공"() {

        given:
        UserSaveRequest request = UserSaveRequest.builder()
                .email("test@naver.com")
                .password("password")
                .userName("테스터")
                .phoneNumber("01000000000")
                .build();

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "유저 로그인 성공"() {

        given:
        UserLoginRequest request = UserLoginRequest.builder()
                .email("test@naver.com")
                .password("password")
                .build();

        User user = UserFixtures.createUser(userId: 1L, passwordEncoder);
        passwordEncoder.encode(user.getPassword()) >> user.getPassword()
        userDetailsService.loadUserByUsername(_ as String) >> new SecurityUser(user)

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/login")
                .session(session)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "유저 로그아웃 성공"() {

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/logout")
                .session(session)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
    }

    def "유저 프로필 성공"() {

        given:
        User user = User.builder()
                .userId(1L)
                .email("test@naver.com")
                .password("password")
                .userName("test")
                .phoneNumber("01000000000")
                .role(Role.USER)
                .passwordEncoder(passwordEncoder)
                .build();
        passwordEncoder.encode(user.getPassword()) >> user.getPassword()
        SecurityUser userDetails = new SecurityUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/profile")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$.userId").value(1L))
                .andExpect(jsonPath("\$.email").value("test@naver.com"))
                .andExpect(jsonPath("\$.userName").value("test"))
                .andExpect(jsonPath("\$.phoneNumber").value("01000000000"))
                .andExpect(jsonPath("\$.role").value(Role.USER.name()))
                .andDo(MockMvcResultHandlers.print())
    }
}
