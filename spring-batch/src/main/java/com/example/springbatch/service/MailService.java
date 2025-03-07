package com.example.springbatch.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;

	public void sendMail(String to, String subject, String text) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			log.error("메일 발송 에러 : ", e);
		} catch (MailAuthenticationException m) {
			log.error("메일 권한 문제 발생 : ", m);
		} catch (Exception e) {
			log.error("메일 관련 알 수 없는 에러 발생 : ", e);
		}
	}

}
