package com.culture.ticketing.notification.api;

import com.culture.ticketing.notification.application.EmitterService;
import com.culture.ticketing.user.domain.SecurityUser;
import com.culture.ticketing.user.domain.User;
import io.swagger.annotations.Api;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Api(tags = {"알림 Controller"})
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final EmitterService emitterService;

    public NotificationController(EmitterService emitterService) {
        this.emitterService = emitterService;
    }

    @GetMapping(value = "/sse-connection", produces = "text/event-stream")
    public SseEmitter stream(final Authentication authentication, @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {

        User user = ((SecurityUser) authentication.getPrincipal()).getUser();

        return emitterService.addEmitter(String.valueOf(user.getUserId()), lastEventId);
    }
}
