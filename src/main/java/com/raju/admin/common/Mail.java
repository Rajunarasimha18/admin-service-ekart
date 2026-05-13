package com.raju.admin.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//import com.datadotlabs.hrms.notificationengine.PasswordAuthentication;

@Component
public class Mail {
   @Value("${file.Server.LocationPath}")
   private String UPLOADED_FOLDER;

   public String getUPLOADED_FOLDER() {
      return this.UPLOADED_FOLDER;
   }

   public void setUPLOADED_FOLDER(String uPLOADED_FOLDER) {
      this.UPLOADED_FOLDER = uPLOADED_FOLDER;
   }
   
//   private final IEmailSettingsDao iEmailSettingsDao;
//
//   public Mail(IEmailSettingsDao iEmailSettingsDao) {
//       this.iEmailSettingsDao = iEmailSettingsDao;
//   }
//   
//   public void sendMail(String toMail, String subject, String textToSend, String fileName, byte[] bytesArray) throws IOException {
//      File propFile = new File(this.UPLOADED_FOLDER + "hrmsmails.properties");
//      Properties props = new Properties();
//      EmailSettings settings = iEmailSettingsDao.findFirstByIsDeletedValueFalseOrderByCreatedDateAsc();
//      String username = settings.getEmail();
//      String password = settings.getPassword();
//      
//      try {
//         FileInputStream fis = new FileInputStream(propFile);
//         props.load(fis);
//         fis.close();
//      } catch (FileNotFoundException var18) {
//      } catch (IOException var19) {
//      }
//
//      Properties properties = System.getProperties();
//      properties.setProperty("mail.smtp.host", props.getProperty("MAIL_HOST"));
//      properties.put("mail.smtp.user", username);
//      properties.put("mail.smtp.password", password);
//    //properties.put("mail.smtp.user", props.getProperty("MAIL_USERNAME"));
//	//properties.put("mail.smtp.password", props.getProperty("MAIL_PASSWORD"));
//      properties.put("mail.smtp.auth", "true");
//      properties.put("mail.smtp.port", props.getProperty("MAIL_PORT"));
//      properties.put("mail.smtp.starttls.enable", "true");
//      properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//      // String username = props.getProperty("MAIL_USERNAME");
//      // String password = props.getProperty("MAIL_PASSWORD");
//      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(username, password);
//			}
//		});
//
//      try {
//         Message message = new MimeMessage(session);
//         message.setFrom(new InternetAddress(props.getProperty("DISPLAY_MAIL"), subject));
//         message.setRecipients(RecipientType.TO, InternetAddress.parse(toMail));
//         message.setSubject(subject);
//         BodyPart messageBodyPart = new MimeBodyPart();
//         messageBodyPart.setText(textToSend);
//         Multipart multipart = new MimeMultipart();
//         multipart.addBodyPart(messageBodyPart);
//         messageBodyPart = new MimeBodyPart();
//         DataSource source = new ByteArrayDataSource(bytesArray, "application/pdf");
//         messageBodyPart.setDataHandler(new DataHandler(source));
//         messageBodyPart.setFileName(fileName);
//         multipart.addBodyPart(messageBodyPart);
//         message.setContent(multipart);
//         Transport.send(message);
//      } catch (MessagingException var17) {
//         throw new RuntimeException(var17);
//      }
//   }
//
//   public void sendMail(String toMail, String ccMail, String subject, String textToSend, MultipartFile file) throws IOException {
//      File propFile = new File(this.UPLOADED_FOLDER + "hrmsmails.properties");
//      Properties props = new Properties();
//      EmailSettings settings = iEmailSettingsDao.findFirstByIsDeletedValueFalseOrderByCreatedDateAsc();
//      String username = settings.getEmail();
//      String password = settings.getPassword();
//      
//      try {
//         FileInputStream fis = new FileInputStream(propFile);
//         props.load(fis);
//         fis.close();
//      } catch (FileNotFoundException var19) {
//      } catch (IOException var20) {
//      }
//
//      Properties properties = System.getProperties();
//      properties.setProperty("mail.smtp.host", props.getProperty("MAIL_HOST"));
//      properties.put("mail.smtp.user", username);
//      properties.put("mail.smtp.password", password);
//	//properties.put("mail.smtp.user", props.getProperty("MAIL_USERNAME"));
//    //properties.put("mail.smtp.password", props.getProperty("MAIL_PASSWORD"));
//      properties.put("mail.smtp.auth", "true");
//      properties.put("mail.smtp.port", props.getProperty("MAIL_PORT"));
//      properties.put("mail.smtp.starttls.enable", "true");
//      properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//      //String username = props.getProperty("MAIL_USERNAME");
//      //String password = props.getProperty("MAIL_PASSWORD");
//      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(username, password);
//			}
//		});
//
//      try {
//         Message message = new MimeMessage(session);
//         message.setFrom(new InternetAddress(props.getProperty("DISPLAY_MAIL"), "Please give reply to all"));
//         message.setRecipients(RecipientType.TO, InternetAddress.parse(toMail));
//         message.setRecipients(RecipientType.CC, InternetAddress.parse(ccMail));
//         message.setSubject(subject);
//         BodyPart messageBodyPart = new MimeBodyPart();
//         messageBodyPart.setText(textToSend);
//         Multipart multipart = new MimeMultipart();
//         multipart.addBodyPart(messageBodyPart);
//         messageBodyPart = new MimeBodyPart();
//         if (file != null) {
//            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
//            DataSource source = new ByteArrayDataSource(file.getBytes(), file.getContentType());
//            messageBodyPart.setDataHandler(new DataHandler(source));
//            messageBodyPart.setFileName(originalFileName);
//            multipart.addBodyPart(messageBodyPart);
//         }
//
//         message.setContent(multipart);
//         Transport.send(message);
//      } catch (MessagingException var18) {
//         throw new RuntimeException(var18);
//      }
//   }
//   
//   
//   public boolean testMail(String toMail, String fromMail, String password) {
//	      File propFile = new File(this.UPLOADED_FOLDER + "hrmsmails.properties");
//	      Properties props = new Properties();
//
//	      try {
//	         FileInputStream fis = new FileInputStream(propFile);
//	         props.load(fis);
//	         fis.close();
//	      } catch (FileNotFoundException var13) {
//	      } catch (IOException var14) {
//	      }
//
//	      Properties properties = System.getProperties();
//	      properties.setProperty("mail.smtp.host", props.getProperty("MAIL_HOST"));
//	     // properties.put("mail.smtp.user", props.getProperty("MAIL_USERNAME"));
//	    // properties.put("mail.smtp.password", props.getProperty("MAIL_PASSWORD"));
//	      properties.put("mail.smtp.user", fromMail);
//	      properties.put("mail.smtp.password", password);
//	      properties.put("mail.smtp.auth", "true");
//	      properties.put("mail.smtp.port", props.getProperty("MAIL_PORT"));
//	      properties.put("mail.smtp.starttls.enable", "true");
//	      properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//	      //String username = props.getProperty("MAIL_USERNAME");
//	      //String password = props.getProperty("MAIL_PASSWORD");
//	      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(fromMail, password);
//				}
//			});
//
//	      try {
//	         Message message = new MimeMessage(session);
//	         message.setFrom(new InternetAddress(props.getProperty("DISPLAY_MAIL")));
//	         message.setRecipients(RecipientType.TO, InternetAddress.parse(toMail));
//	         message.setSubject("Test Mail");
//	         message.setText("Test");
//	         Transport.send(message);
//	         return true;
//	      } catch (MessagingException var12) {
//	         return false;
//	      }
//
//	   }
//   
//
//   public void sendMail(String toMail, String subject, String textToSend) {
//      File propFile = new File(this.UPLOADED_FOLDER + "hrmsmails.properties");
//      Properties props = new Properties();
//      EmailSettings settings = iEmailSettingsDao.findFirstByIsDeletedValueFalseOrderByCreatedDateAsc();
//      String username = settings.getEmail();
//      String password = settings.getPassword();
//
//      try {
//         FileInputStream fis = new FileInputStream(propFile);
//         props.load(fis);
//         fis.close();
//      } catch (FileNotFoundException var13) {
//      } catch (IOException var14) {
//      }
//
//      Properties properties = System.getProperties();
//      properties.setProperty("mail.smtp.host", props.getProperty("MAIL_HOST"));
//      properties.put("mail.smtp.user", username);
//      properties.put("mail.smtp.password", password);
//    //properties.put("mail.smtp.user", props.getProperty("MAIL_USERNAME"));
//    //properties.put("mail.smtp.password", props.getProperty("MAIL_PASSWORD"));
//      properties.put("mail.smtp.auth", "true");
//      properties.put("mail.smtp.port", props.getProperty("MAIL_PORT"));
//      properties.put("mail.smtp.starttls.enable", "true");
//      properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
////      String username = props.getProperty("MAIL_USERNAME");
////      String password = props.getProperty("MAIL_PASSWORD");
//      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(username, password);
//			}
//		});
//
//      try {
//         Message message = new MimeMessage(session);
//         message.setFrom(new InternetAddress(props.getProperty("DISPLAY_MAIL")));
//         message.setRecipients(RecipientType.TO, InternetAddress.parse(toMail));
//         message.setSubject(subject);
//         message.setText(textToSend);
//         Transport.send(message);
//      } catch (MessagingException var12) {
//      }
//
//   }
   
   
	public String md5Generator(String value) {
		String md5Hash=null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] hashBytes = md.digest(value.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            md5Hash= sb.toString();
            System.out.println("Input: " + value);
            System.out.println("MD5 Hash: " + md5Hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("MD5 algorithm not found!");
        }
		return md5Hash;
	}
   
}
