package com.todoapplication.app.serviceImpls;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todoapplication.app.dtos.UserRegistrationDTO;
import com.todoapplication.app.entities.Roles;
import com.todoapplication.app.entities.Users;
import com.todoapplication.app.exceptions.EmptyTextFieldException;
import com.todoapplication.app.exceptions.UserLoginException;
import com.todoapplication.app.repositories.UserRepository;
import com.todoapplication.app.services.UserService;
import com.todoapplication.app.services.RoleService;
@Service
public class UsersServiceImpl implements UserService{
	private static Logger logger= LoggerFactory.getLogger(UsersServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleService roleService;
	@Autowired
	RefreshTokenServiceImpl refreshTokenService;
	@Value("${app.token.password.reset.duration}")
	private Long expiration;
	PasswordEncoder passwordEncoder;
	
	@Override
	public Users createUser(UserRegistrationDTO userDTO) {
		logger.info("Adding User");
		Users newUser=new Users();
		try {
			if(userDTO.getEmail().isEmpty() && userDTO.getFirstName().isEmpty()) {
				throw new EmptyTextFieldException("Empty Text Field! Fill Up Empty Text Field");
			}
			Users user=userRepository.findByEmail(userDTO.getEmail()).get();
			System.out.println("kjbs;ufbv;uvb;11");
			if(user==null) {
				boolean isUserAnAdmin=userDTO.getRegisterAsAdmin();
				System.out.println("kjbs;ufbv;uvb;10");
				newUser.setActive(true);
				System.out.println("kjbs;ufbv;uvb;8");
				newUser.setEmail(userDTO.getEmail());
				System.out.println("kjbs;ufbv;uvb;7");
				newUser.setEmailVerified(false);
				System.out.println("kjbs;ufbv;uvb;6");
				newUser.setFirstName(userDTO.getFirstName());
				System.out.println("kjbs;ufbv;uvb;5");
				newUser.setLastName(userDTO.getLastName());
				System.out.println("kjbs;ufbv;uvb;4");
				newUser.setPhone(userDTO.getPhone());
				System.out.println("kjbs;ufbv;uvb;3");
				newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
				newUser.addRoles(getRolesForNewUser(isUserAnAdmin));
				System.out.println("kjbs;ufbv;uvb;2");
				userRepository.save(newUser);
				System.out.println("kjbs;ufbv;uvb;1");
				sendEmailVerification(newUser.getEmail(), newUser.getFirstName());
				
				return newUser;
			}else {
				throw new UserLoginException("Email or Username Already Taken");
			}
			
		}catch(Exception e) {
			logger.error("Error Implementing Creating A New User"+e);
			return null;
		}
		
	}

	private Set<Roles> getRolesForNewUser(boolean isUserAnAdmin) {
		 Set<Roles> newUserRoles = new HashSet<>(roleService.findAll());
	        if (!isUserAnAdmin) {
	            newUserRoles.removeIf(Roles::isAdminRole);
	        }
	        logger.info("Setting user roles: " + newUserRoles);
	        return newUserRoles;
	}

	@Override
	public int performLogout(String token) {
		// TODO Auto-generated method stub
		logger.info("Performing Logout");
		try {
			refreshTokenService.deleteToken(token);
			return 1;
		}catch(Exception e) {
			logger.error("Unable To Implement Log-out" +e);
			return 0;
		}
		
	}
	 public void sendEmailVerification(String email, String userName)
	            throws IOException, MessagingException,AddressException{
	    	Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				   protected PasswordAuthentication getPasswordAuthentication() {
				      return new PasswordAuthentication("ojoGideon5@gmail.com", "gidiboy156321");
				   }
				});
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("ojoGideon5@gmail.com", false));

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			msg.setSubject("EMAIL VERIFICATION LINK- ");
			msg.setContent("Hello "+email+",<br>To Reset your password. Click the link <a href='http://localhost:4200/administrator/verify?link= "+"'>here</a> to login.", "text/html");
		    msg.setSentDate(new Date());

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent("Hello "+email+",<br>Confirm your account. Visit <a href='http://localhost:4200/administrator/welcome?userID="+"'>here</a> to login.", "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			//MimeBodyPart attachPart = new MimeBodyPart();

			//attachPart.attachFile("/var/tmp/image19.png");
			//multipart.addBodyPart(attachPart);  
			msg.setContent(multipart);
			Transport.send(msg); 
	    }
	 public void sendResetLink(String resetPasswordLink, String email)
	            throws IOException, MessagingException,AddressException {
	        Long expirationInMinutes = TimeUnit.MILLISECONDS.toMinutes(expiration);
	        String expirationInMinutesString = expirationInMinutes.toString();
	        Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				   protected PasswordAuthentication getPasswordAuthentication() {
				      return new PasswordAuthentication("ojoGideon5@gmail.com", "gidiboy156321");
				   }
				});
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("ojoGideon5@gmail.com", false));

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			msg.setSubject("PASSWORD RESET LINK- ");
			msg.setContent("Hello "+email+",<br>To Reset your password. Click the link <a href='http://localhost:4200/administrator/verify?link= "+"'>here</a> to login."
					+ "expires in " + expirationInMinutesString, "text/html");
		    msg.setSentDate(new Date());

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent("Hello "+email+",<br>Confirm your account. Visit <a href='http://localhost:4200/administrator/welcome?userID="+"'>here</a> to login.", "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			//MimeBodyPart attachPart = new MimeBodyPart();

			//attachPart.attachFile("/var/tmp/image19.png");
			//multipart.addBodyPart(attachPart);  
			msg.setContent(multipart);
			Transport.send(msg); 

	       
	    }

}
