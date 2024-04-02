package com.culture.ticketing.common.utils;

import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.user.domain.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    private static final String FROM_ADDRESS = "choijuna052@gmail.com";

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public SimpleMailMessage getBookingStartAlarmText(User user, Show show) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setFrom(FROM_ADDRESS);
        message.setSubject("[공연 예약 시작] '" + show.getShowName() + "' 공연 예약이 곧 시작됩니다!");
        message.setText("안녕하세요.\n" +
                user.getUserName() + " 님께서 관심 있게 보는 공연의 예약이 곧 시작됩니다.\n" +
                show.getBookingStartDateTime() + " 부터 예약이 가능합니다.");

        return message;
    }

    public void sendMail(SimpleMailMessage message) {

        javaMailSender.send(message);
    }
}
