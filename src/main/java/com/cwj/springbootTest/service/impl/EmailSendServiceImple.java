package com.cwj.springbootTest.service.impl;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cwj.springbootTest.service.EmailSendService;

@Service
public class EmailSendServiceImple implements EmailSendService {
	private final Logger logger = LoggerFactory.getLogger(EmailSendServiceImple.class);
	@Autowired
	private JavaMailSender mailSender;

	@Value("${mail.fromMail.addr}")
	private String from;

	@Override
	public void simpleEmailSend(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);

		try {
			mailSender.send(message);
			System.out.println("Sussess");
			logger.info("简单邮件已经发送。");
		} catch (Exception e) {
			logger.error("发送简单邮件时发生异常！", e);
		}
	}

	@Override
	public void htmlEmailSend(String to, String subject, String content) {
		// TODO Auto-generated method stub
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
			mimeMessageHelper.setFrom(from);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(content, true);

			mailSender.send(message);
			logger.info("html邮件发送成功");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			logger.error("发送html邮件发生异常!", e);
		}
	}

	@Override
	public void attachmentsMailSend(String to, String subject, String content, MultipartFile[] files) {
		// TODO Auto-generated method stub
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
			mimeMessageHelper.setFrom(from);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(content, true);
			if(files!=null){
				
				for (int i = 0; i < files.length; i++) {
					// 上传文件
					File newfile = null;
					try {
						newfile=File.createTempFile("tmp", null);
						files[i].transferTo(newfile);
					    newfile.deleteOnExit();	        
					} catch (IOException e) {
					    e.printStackTrace();
					}
					mimeMessageHelper.addAttachment(files[i].getOriginalFilename(), newfile);
				}
			}
			

			mailSender.send(message);
			logger.info("带附件邮件发送成功");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			logger.error("发送html邮件发生异常!", e);
		}

	}

	@Override
	public void inlineResourceMailSend(String to, String subject, String content,String path,String reCid) {
		// TODO Auto-generated method stub
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
			mimeMessageHelper.setFrom(from);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(content, true);
			
			FileSystemResource res = new FileSystemResource(new File(path));
			mimeMessageHelper.addInline(reCid, res);
			mailSender.send(message);
			logger.info("静态资源邮件发送成功");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			logger.error("静态资源邮件发生异常!", e);
		}
	}
}
