package com.cwj.springbootTest.service;

import org.springframework.web.multipart.MultipartFile;

public interface EmailSendService {
	void simpleEmailSend(String to, String subject, String content);
	void htmlEmailSend(String to, String subject, String content);
	void attachmentsMailSend(String to, String subject, String content,MultipartFile[] files);
	void inlineResourceMailSend(String to, String subject, String content,String path,String reCid);
}
