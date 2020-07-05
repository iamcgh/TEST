package com.test0617.utils;

/*邮箱工具类*/

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

	public static void sendMail(String email, String emailMsg)
			throws AddressException, MessagingException {
		// 1.创建一个程序与邮件服务器会话对象 Session

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");
		props.setProperty("mail.smtp.host", "smtp.sina.com");
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true
		props.setProperty("mail.smtp.port", "25");	//设置传输端口
		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("cghyx123456test", "b123b4b8ba095894");
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("cghyx123456test@sina.com")); // 设置发送者

		message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者

		message.setSubject("[MY-USER]账号邮箱注册激活邮件");
		// message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

		message.setContent(emailMsg, "text/html;charset=utf-8");
		
		//防止被当成垃圾邮件
		message.addHeader("X-Priority", "3");
		message.addHeader("X-MSMail-Priority", "Normal");
		message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
		message.addHeader("X-MimeOLE", "Produced By Microsoft MimeOLE V6.00.2900.2869");
		message.addHeader("ReturnReceipt", "1");

		// 3.创建 Transport用于将邮件发送

		Transport.send(message);
	}
}
