package com.ezra.programandojuntos.models.services;

import com.ezra.programandojuntos.dto.EmailDto;

public interface IEmailService {
	public void sendMailWithTymeleaf(EmailDto email);
}
