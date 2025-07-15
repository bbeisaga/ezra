package com.ezra.programandojuntos.models.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ezra.programandojuntos.dto.EmailDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements IEmailService {
	
	@Value("${app.urlfront}")
	private String urlfront;

	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;
	
	
	public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
		super();
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
	}

	@Override
	@Async
	public void sendMailWithTymeleaf(EmailDto email) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			Context context = new Context();
			context.setVariable("saludo", email.getSaludo());
			context.setVariable("mensaje", email.getMensaje());
			context.setVariable("urlfront", urlfront);

			String contentHtml = templateEngine.process("credenciales_acceso", context);
			
			mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
			mimeMessageHelper.setTo(email.getEmailDestino());
			mimeMessageHelper.setSubject(email.getAsunto());
			//mimeMessageHelper.setFrom("bbeisaga@gmail.com");
			mimeMessageHelper.setText(contentHtml, true);
			javaMailSender.send(message);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
