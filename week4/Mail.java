/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;


/**
 * Mail class sends the mail with attachments, subject and body to the given mails
 */
public class Mail {
    private static final Logger logger = LogManager.getLogger(Mail.class);

    public static void main(String[] args) {
        String message = "Hello team,\nHere are the converted format reports.\nRegards,\nMaheswar Reddy";
        String subject = "System Generated Reports for 2022-09-30";
        String[] to ={"krupa@codeops.tech","sudharshan@codeops.tech","indukurimr@azuga.com","adarshs@azuga.com","aparajitam@azuga.com","ashoop@azuga.com",
                "dushyants@azuga.com","kartiks@azuga.com","lokanathk@azuga.com","pruthvikp@azuga.com","rajatt@azuga.com",
                "rishabh@azuga.com","satvikm@azuga.com","suryaps@azuga.com","vijayyv@azuga.com"};
        String from = "maheshroman143@gmail.com";
        for (String toMail : to) {
            sendAttach(message, subject, toMail, from);
        }
    }

    /**
     * It sends the mail with attachments subject and body to the given mails
     * @param message It is to display the message in the body
     * @param subject It is to set subject to the sending mail
     * @param to It is to get the mail address of person to send
     * @param from It is to get the mail address from who to send
     */
    private static void sendAttach(String message, String subject, String to, String from) {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        logger.info("PROPERTIES " + properties);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("maheshroman143@gmail.com", "rzkiuimpeaqpcctq");
            }
        });
        session.setDebug(true);


        MimeMessage m = new MimeMessage(session);

        try {
            m.setFrom(from);

            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            m.setSubject(subject);


            String path = "/users/azuga/documents/forrmdir/TrainingJava/Files/Reports.zip";

            MimeMultipart mimeMultipart = new MimeMultipart();

            MimeBodyPart textMine = new MimeBodyPart();

            MimeBodyPart fileMime = new MimeBodyPart();

            try {
                textMine.setText(message);

                File file = new File(path);

                fileMime.attachFile(file);
                mimeMultipart.addBodyPart(textMine);
                mimeMultipart.addBodyPart(fileMime);
                m.setContent(mimeMultipart);
                Transport.send(m);
            } catch (IOException e) {
                logger.error("Error in attaching the {}", path);
            }

            logger.info("sent success");
        } catch (MessagingException e) {
            logger.error("Messaging Exception {}",e);
        }
    }
}
