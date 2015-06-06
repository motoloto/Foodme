package com.mail.controller;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
	// 設定email帳號 & 密碼
	private final String username = "motoloto0329@gmail.com";
	private final String password = "biejqieuzwwehvqd";

	// 傳入員工姓名、密碼、E-mail address
	public void sendPassword(String cName,String cMail, String rest_account, String rest_psw)
			throws MessagingException, UnsupportedEncodingException {
		
		// 設定使用SSL連線至Gmail server
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("motoloto0329@gmail.com","UTF-8"));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(cMail));
		message.setSubject("通過加盟審查通知"+cName);
		message.setText("恭喜通過加盟審查，" +cName+"\n您的的帳號：" +rest_account+"\n密碼："+rest_psw);

		Transport.send(message);
		//System.out.println("Done");
	}
	// 傳入員工姓名、密碼、E-mail address
	public void sendOdrInfo(String cMail, String name)
			throws MessagingException, UnsupportedEncodingException {
		
		// 設定使用SSL連線至Gmail server
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("motoloto@livemail.tw","UTF-8"));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(cMail));
		message.setSubject("成功建立訂單通知信(*此為系統自動發信，請勿回覆)");
		message.setText("恭喜完成訂單" +name+"的訂單明細如下\n"+  "XXX ,\n\n " );
		
		Transport.send(message);
		//System.out.println("Done");
	}
	public void sendAdvice(String cName,String cMail, String title, String content)
			throws MessagingException, UnsupportedEncodingException {
		
		// 設定使用SSL連線至Gmail server
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(cMail,"UTF-8"));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("motoloto0329@gmail.com"));
		message.setSubject(title);
		message.setText("來自" +cMail+"\n"+ cName + "的意見回覆 ,\n\n " +content);

		Transport.send(message);
		//System.out.println("Done");
	}
	public void returnAffiAppli(String reason, String rest_name)throws MessagingException, UnsupportedEncodingException {

		// 設定使用SSL連線至Gmail server
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("motoloto@livemail.tw","UTF-8"));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("daily.wu@gmail.com"));
		message.setSubject(rest_name);
		message.setText(reason);

		Transport.send(message);
		//System.out.println("Done");
		
	}
}
