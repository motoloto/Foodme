package com.odr.controller;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailServiceForBuyCoupon {
	// 設定email帳號 & 密碼
	private final String username = "shine5603@gmail.com";
	private final String password = "shine512072";

	// 傳入員工姓名、密碼、E-mail address
	public void sendPassword(String empName, String memMail,String cop_name,Integer odr_buyamt,Integer odr_toprc)
			throws MessagingException {
		
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
		
		String messageText  = "Hello! " + empName + " ,您已成功購買商品\n"+
                "訂單已成立，請盡速付款。\n"+
                "商品名稱: "+cop_name+"\n"+
                "購買數量: "+odr_buyamt+"張\n"+
                "購買金額: "+odr_toprc+"元(NT)\n\n"+
                "用餐資訊查詢 http://localhost:8081/YA101G5/front/indexXXX.jsp\n"+
                "免付費意見專線： 0800-3308000\n"+
                "Email: service@FOODME.com.tw\n"+
                "FOOODme品牌小組\n";               

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("shine5603@gmail.com"));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(memMail));
		message.setSubject("FOODme線上餐廳訂位系統，購買訂單成立通知");
		message.setText(messageText);

		Transport.send(message);
		//System.out.println("Done");
	}
}
