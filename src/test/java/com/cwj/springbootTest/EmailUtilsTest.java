package com.cwj.springbootTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.cwj.springbootTest.service.EmailSendService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailUtilsTest {
	@Autowired
	private EmailSendService emailSendService;
	@Autowired
	private TemplateEngine templateEngine;

	@Test
	public void simpleMailSendTest() {
		emailSendService.simpleEmailSend("957616331@qq.com", "元宵节日快乐!", "Dear Chen,Happy Lantern Festival! ");
	}

	@Test
	public void htmlMailSendTest() {
		String content = "<html>\n" + "<body>\n" + "    <h3>hello world ! 这是一封Html邮件!</h3>\n" + "</body>\n" + "</html>";
		emailSendService.htmlEmailSend("957616331@qq.com", "元宵节日快乐!", content);
	}

	@Test
	public void inlineResourceMailSendTest() {
		String cid = "0219";
		String path = "F:\\图片\\无脸男.jpg";
		String content = "<html><body>这是有图片的邮件：<img src=\'cid:" + cid + "\' ><img src=\'cid:" + cid + "\' ></body></html>";
		emailSendService.inlineResourceMailSend("957616331@qq.com", "元宵节日快乐!", content, path, cid);
	}
	
	//模板方式发送邮件
	@Test
	public void sendTemplateMail() {
		// 创建邮件正文
		Context context = new Context();
		context.setVariable("name", "DaoZhang");
		String emailContent = templateEngine.process("template/emailTemplate", context);

		emailSendService.htmlEmailSend("957616331@qq.com", "Apex", emailContent);
	}
}
