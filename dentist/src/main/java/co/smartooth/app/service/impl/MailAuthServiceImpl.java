package co.smartooth.app.service.impl;

import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import co.smartooth.app.utils.AES256Util;
import co.smartooth.app.vo.MailAuthVO;
import co.smartooth.app.auth.MailAuthInfo;
import co.smartooth.app.mapper.MailAuthMapper;
import co.smartooth.app.service.MailAuthService;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 04. 28
 * 수정일 : 2023. 08. 07
 * 서버분리 : 2023. 08. 01
 */
@Service
@PropertySource(value = { "classpath:application.yml"})
public class MailAuthServiceImpl implements MailAuthService {

	@Autowired(required = false)
	MailAuthMapper mailAuthMapper;

	// 인증번호 생성
	int size;

	private String getKey(int size) {
		this.size = size;
		return getAuthCode();
	}

	// 이메일 인증번호 난수 발생
	private String getAuthCode() {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		int num = 0;

		while (buffer.length() < size) {
			num = random.nextInt(10);
			buffer.append(num);
		}

		return buffer.toString();
	}
	
	
	
	
	
	// 비밀번호 재설정 이메일 발송
	@Override
	public void sendResetPasswordMail(String userId, String userEmail, String emailAuthKey) throws Exception{
		
		// 아이디 암호화
		String encordingUserId = "";
		
		AES256Util aes256Util = new AES256Util(); 
		
		Properties prop = System.getProperties();
		// 로그인시 TLS를 사용할 것인지 설정
		prop.put("mail.smtp.starttls.enable", "true");
		// 이메일 발송을 처리해줄 SMTP서버
		//prop.put("mail.smtp.host", "smtp.gmail.com");
		//prop.put("mail.smtp.host", "smtp.naver.com");
		prop.put("mail.smtp.host", "smtp.worksmobile.com");
		// SMTP 서버의 인증을 사용한다는 의미
		prop.put("mail.smtp.auth", "true");
		// TLS의 포트번호는 587이며 SSL의 포트번호는 465이다.
		prop.put("mail.smtp.port", "587");
		// 프로토콜 관련 예외처리를 위한 TLS 버전 지정		 
		prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
	 
		Authenticator auth = new MailAuthInfo();
		MailAuthInfo mailAuthInfo = new MailAuthInfo();
		Session session = Session.getDefaultInstance(prop, auth);
		MimeMessage msg = new MimeMessage(session);
			
		// 서버 정보를 application.properties에서 호출하여 사용
		String serverInfo = mailAuthInfo.getServerInfo();
		// serverInfo = serverInfo.replaceAll("8080", "3000");
		String senderId = mailAuthInfo.getUsername();
		String sernderName = mailAuthInfo.getSenderName();
		
		MailAuthVO mailAuthVO = new MailAuthVO();
		mailAuthVO.setUserId(userId);
		// 메일로 전송할 파라미터 아이디 암호화
		encordingUserId = aes256Util.aesEncode(userId);
	
		// 보내는 날짜 지정
		msg.setSentDate(new Date());
		// 발송자를 지정한다. 발송자의 메일, 발송자명
		msg.setFrom(new InternetAddress(senderId, sernderName));
		// 수신자의 메일을 생성한다.
		InternetAddress to = new InternetAddress(userEmail);
		// Message 클래스의 setRecipient() 메소드를 사용하여 수신자를 설정한다. setRecipient() 메소드로 수신자, 참조,
		// Message.RecipientType.TO : 받는 사람 // Message.RecipientType.CC : 참조 // Message.RecipientType.BCC : 숨은 참조
		msg.setRecipient(Message.RecipientType.TO, to);
		// 메일의 제목 지정
		msg.setSubject("비밀번호 재설정", "UTF-8");
	
		/**
		 * 비밀번호 초기화 이메일 인증
		 * */
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>")
			.append("<html xmlns='http://www.w3.org/1999/xhtml'>")
			.append("	<head>")
			.append("		<meta http-equiv='Content-Type' content='text/html charset=utf-8' />")
			.append("		<title>비밀번호 재설정 ::: 스마투스코리아</title>")
			.append("	</head>")
			.append("	<tbody style='width: 1024px'>")
			.append("		<tr>")
			.append("			<td height='45' colspan='3' bgcolor='#FFFFFF'></td>")
			.append("		</tr>")
			.append("		<tr>")
			.append("			<td height='25' colspan='2' bgcolor='#FFFFFF' align='center'>")
			.append("				<img src='https://www.smartooth.co/img/logo_origin.png' width='200px' alt='smartooth' border='0' style='display:block;'>")
			.append("			</td>")
			.append("		</tr>")
			.append("		<tr>")
			.append("			<td colspan='2' align='center'>")
			.append("			<br/>")
			.append("				<h2 style='margin-top:15px; margin-bottom:10px; margin-left: px; font-size:18px; font-weight:lighter;'>"
											+ "스마투스코리아 공식 메일로 전송하는 비밀번호 재설정 이메일입니다.<br/>"
											+ "아래의 링크에 접속하여 암호를 재설정할 수 있습니다.<br/>"
											+ "(암호 재설정 사이트는 24시간 동안 유효합니다.)</h2>")
			.append("				<div style='width: 100%;'>")
			.append("					<div style='width:700px;'>")
			.append("						<div>")
			.append("							<br/>")
			.append("							<a href='http://")
			// API URL 설정 부분 // 아이디와 인증번호를 암호화 한 상태에서 메일을 발송
			.append(serverInfo)
			.append("/dentist/utils/resetUserPwd.do?userId=")
			.append(encordingUserId)
			.append("&emailAuthKey=")
			.append(emailAuthKey)
			// API URL 설정 부분 ////////////////////////////////////////////////
			.append("' style='font-size: 20px; font-weight:bold;'>비밀번호 변경</a>")
			.append("						</div>")
			.append("						<br/>")
			.append("						<br/>")
			.append("						<p style='line-height: 1.4em; font-weight:bold'></p>")
			.append("						<p style='font-size: 11px; color:#636363'></p>")
			.append("					</div>")
			.append("				</div>")
			.append("				<div style='font-size: 11px; color:#636363; background-color:#F4F4F4; line-height:1.3em; padding:20px 30px; margin-top:25px'>"
											+"기타 문의 사항은 웹 사이트(<a href='http://smartooth.co/about_us' target='_blank'>www.smartooth.co)</a>를 방문하시거나<br/>"
											+ "스마투스 콘택트 메일(<a href='mailto:contact@smartooth.co'>contact@smartooth.co</a>)로 연락주시기 바랍니다.")
			.append("					<br/>")
			.append("				</div>")
			.append("			</td>")
			.append("		</tr>")
			.append("	</body>")
			.append("</html>");
				
			// text로만 보내고 싶을 경우 setText를 사용 (msg.setText(sb.toString());)
			// html로 보내고 싶을 경우 setContents 사용
			msg.setContent(sb.toString(), "text/html;charset=utf-8");
			// Transport는 메일을 최종적으로 보내는 클래스로 메일을 전송			
			Transport.send(msg);
		}
	
	
	
	
	
		// 회원가입 이메일 인증
		@Override
		public void sendMail(String userId) throws Exception {
	
			int isIdExist = 0;
			String authKey = getKey(6);
			String encAuthKey = "";
			String encId = "";
	
			AES256Util aes256Util = new AES256Util();
			// 이메일 인증번호 (6자리 난수) 생성
	
			try {
				encAuthKey = aes256Util.aesEncode(authKey);
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			Properties prop = System.getProperties();
			// 로그인시 TLS를 사용할 것인지 설정
			prop.put("mail.smtp.starttls.enable", "true");
			// 이메일 발송을 처리해줄 SMTP서버
			// prop.put("mail.smtp.host", "smtp.gmail.com");
			// prop.put("mail.smtp.host", "smtp.naver.com");
			prop.put("mail.smtp.host", "smtp.worksmobile.com");
			// SMTP 서버의 인증을 사용한다는 의미
			prop.put("mail.smtp.auth", "true");
			// TLS의 포트번호는 587이며 SSL의 포트번호는 465이다.
			prop.put("mail.smtp.port", "587");
			// 프로토콜 관련 예외처리를 위한 TLS 버전 지정
			prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
	
			Authenticator auth = new MailAuthInfo();
			MailAuthInfo mailAuthInfo = new MailAuthInfo();
			Session session = Session.getDefaultInstance(prop, auth);
			MimeMessage msg = new MimeMessage(session);
	
			// 서버 정보를 application.properties에서 호출하여 사용
			String serverInfo = mailAuthInfo.getServerInfo();
			String senderId = mailAuthInfo.getUsername();
			String sernderName = mailAuthInfo.getSenderName();
	
			MailAuthVO dentistMailAuthVO = new MailAuthVO();
			dentistMailAuthVO.setUserId(userId);
			dentistMailAuthVO.setAuthKey(authKey);
			// 메일로 전송할 파라미터 아이디 암호화
			encId = aes256Util.aesEncode(userId);
	
			try {
				isIdExist = mailAuthMapper.selectMailAuthInfo(dentistMailAuthVO);
				if (isIdExist == 0) {
					// 이메일 발송 전 authKey를 Database에 UPDATE
					insertAuthKeyById(dentistMailAuthVO);
				} else {
					// 아이디가 존재한다면 UPDATE
					updateAuthKeyById(dentistMailAuthVO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			// 보내는 날짜 지정
			msg.setSentDate(new Date());
			// 발송자를 지정한다. 발송자의 메일, 발송자명
			msg.setFrom(new InternetAddress(senderId, sernderName));
			// 수신자의 메일을 생성한다.
			InternetAddress to = new InternetAddress(userId);
			// Message 클래스의 setRecipient() 메소드를 사용하여 수신자를 설정한다. setRecipient() 메소드로 수신자, 참조,
			// Message.RecipientType.TO : 받는 사람 // Message.RecipientType.CC : 참조 //
			// Message.RecipientType.BCC : 숨은 참조
			msg.setRecipient(Message.RecipientType.TO, to);
			// 메일의 제목 지정
			msg.setSubject("Sign-up E-mail verification", "UTF-8");
	
			/**
			 * 회원가입 이메일 인증
			 */
			StringBuffer sb = new StringBuffer();
			sb.append(
					"<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>")
					.append("<html xmlns='http://www.w3.org/1999/xhtml'>").append("	<head>")
					.append("		<meta http-equiv='Content-Type' content='text/html charset=utf-8' />")
					.append("		<title>Sign-up confirmation email ::: Smartooth Korea</title>").append("	</head>")
					.append("	<tbody style='width: 1024px'>").append("		<tr>")
					.append("			<td height='45' colspan='3' bgcolor='#FFFFFF'></td>").append("		</tr>")
					.append("		<tr>").append("			<td height='25' colspan='2' bgcolor='#FFFFFF' align='center'>")
					.append("				<img src='https://www.smartooth.co/imgs/logo_origin.png' width='200px' alt='smartooth' border='0' style='display:block;'>")
					.append("			</td>").append("		</tr>").append("		<tr>")
					.append("			<td colspan='2' align='center'>").append("			<br/>")
					.append("				<h2 style='margin-top:15px; margin-bottom:10px; margin-left: px; font-size:18px; font-weight:lighter;'>Please click the link below to continue your membership registration.</h2>")
					.append("				<div style='width: 100%;'>")
					.append("					<div style='width:700px;'>").append("						<div>")
					.append("							<br/>").append("							<a href='http://")
					// API URL 설정 부분 // 아이디와 인증번호를 암호화 한 상태에서 메일을 발송
					.append(serverInfo).append("/app/user/signUp/emailConfirm.do?userId=").append(encId).append("&authKey=")
					.append(encAuthKey)
					// API URL 설정 부분 ////////////////////////////////////////////////
					.append("' style='font-size: 20px; font-weight:bold;'>Click here to Sign-up</a>")
					.append("						</div>")
					.append("						<p style='line-height: 1.4em; font-weight:bold'></p>")
					.append("						<p style='font-size: 11px; color:#636363'></p>")
					.append("					</div>").append("				</div>")
					.append("				<div style='font-size: 11px; color:#636363; background-color:#F4F4F4; line-height:1.3em; padding:20px 30px; margin-top:25px'>")
					.append("					This e-mail is for outgoing only, so it is not possible to check it when making inquiries by e-mail.")
					.append("					<br/>")
					.append("					For other questions, please visit the website (<a href='http://smartooth.co/about_us' target='_blank'>www.smartooth.co)</a> <br/> Or Please contact us by email. <a href='mailto:﻿smartooth@smartooth.co'>smartooth@smartooth.co</a>")
					.append("					<br/>").append("				</div>").append("			</td>")
					.append("		</tr>").append("	</body>").append("</html>");
	
			// text로만 보내고 싶을 경우 setText를 사용 (msg.setText(sb.toString());)
			// html로 보내고 싶을 경우 setContents 사용
			msg.setContent(sb.toString(), "text/html;charset=utf-8");
			// Transport는 메일을 최종적으로 보내는 클래스로 메일을 전송
			Transport.send(msg);
		}
	
		// 아이디와 인증 번호를 INSERT
		@Override
		public void insertAuthKeyById(MailAuthVO dentistMailAuthVO) throws Exception {
			mailAuthMapper.insertAuthKeyById(dentistMailAuthVO);
		}
	
		// 아이디와 인증 번호를 UPDATE
		@Override
		public void updateAuthKeyById(MailAuthVO dentistMailAuthVO) throws Exception {
			mailAuthMapper.updateAuthKeyById(dentistMailAuthVO);
		}
	
		// 인증 메일 클릭 시 인증 상태를 'Y' 로 업데이트
		@Override
		public void updateAuthStatusY(String userId) throws Exception {
			mailAuthMapper.updateAuthStatusY(userId);
		}
	
		// 인증 메일 요청 시 인증 상태를 'N' 로 업데이트
		@Override
		public void updateAuthStatusN(String userId) throws Exception {
			mailAuthMapper.updateAuthStatusN(userId);
		}
	
		// 사용자 메일 인증 여부 확인
		@Override
		public String isEmailAuthEnabled(String userId) throws Exception {
			return mailAuthMapper.isEmailAuthEnabled(userId);
		}

}
