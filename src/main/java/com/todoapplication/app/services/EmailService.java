//package com.todoapplication.app.services;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.Properties;
//import java.util.concurrent.TimeUnit;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Multipart;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//@Service
//public class EmailService {
//
//    private final JavaMailSender mailSender;
//
//    private String mailFrom;
//
//   
//
//    @Autowired
//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//   
//
//    /**
//     * Setting the mail parameters.Send the reset link to the respective user's mail
//     */
//   
//}
