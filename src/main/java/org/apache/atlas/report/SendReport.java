package org.apache.atlas.report;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
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

import org.atlas.testHelper.AtlasConstants;

@SuppressWarnings("restriction")
public class SendReport {

	public static void main(String[] args) {
		sendReportByEmail("fayazm@mprglobalsolutions.com",
				"Fayaz@786", "fayazm@mprglobalsolutions.com",
				"Test Excution Report", "");
	}
	
	static Properties props = null;
	static String host = "smtp.gmail.com";

	private static void loadProperties(String from, String pass) {
		props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
	}

	private static Session getSession(final String from, final String password) {
		loadProperties(from, password);
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from, password);
					}
				});
		// Session session = Session.getDefaultInstance(props);
		return session;

	}

	public static void sendReportByEmail(String fromAddress,
			String password, String toAddress, String subject, String body) {

		Session session = getSession(fromAddress, password);
		MimeMessage message = new MimeMessage(session);
		try {
			// Set from address
			message.setFrom(new InternetAddress(fromAddress));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					toAddress));
			// Set subject
			message.setSubject(subject);
			message.setText(body);
			BodyPart objMessageBodyPart = new MimeBodyPart();
			objMessageBodyPart.setText("Please Find The Attached Report File!");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(objMessageBodyPart);
			objMessageBodyPart = new MimeBodyPart();

			// Set path to the pdf report file
			String filename = AtlasConstants.REPORT_FILE_PATH;

			// Create data source to attach the file in mail
			DataSource source = new FileDataSource(filename);
			objMessageBodyPart.setDataHandler(new DataHandler(source));
			objMessageBodyPart.setFileName(filename.substring(
					filename.lastIndexOf("\\") + 1, filename.length()));
			multipart.addBodyPart(objMessageBodyPart);
			message.setContent(multipart);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, fromAddress, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
}
