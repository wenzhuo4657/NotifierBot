package cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy.email;




import cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy.*;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;

import java.util.Properties;

public class EmailNotifier extends IAbstractNotifier<GmailConfig, NotifierMessage,NotifierResult> {


    public EmailNotifier(GmailConfig config) {
        super(config);
    }
    Session session;

  @Override
    public NotifierResult send(NotifierMessage message) {

        GmailConfig config = getConfig();
        String from = config.getFrom();
        String to = config.getTo();
        String title = message.getTitle();
        String content = message.getContent();
        File file = message.getFile();


        try {
            Session session = getSession(config.getFrom(), config.getPassword());

            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress( from, "DailyWeb"));
            msg.setReplyTo(new Address[]{new InternetAddress(to)});
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to,  false));
            msg.setSubject( title);


            MimeBodyPart html = new MimeBodyPart();
            html.setContent("<p>"+content+"</p>", "text/html; charset=UTF-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(html);

            if (file != null){
                MimeBodyPart attach = new MimeBodyPart();
                attach.attachFile(file);
                multipart.addBodyPart(attach);
            }



            msg.setContent(multipart);


            Transport.send(msg);
        }catch (Exception e){
            e.printStackTrace();
            return NotifierResult.fail();
        }


        return NotifierResult.ok();
    }



    @Override
    public boolean isAvailable() {
        try {
            NotifierMessage message = new NotifierMessage();
            message.setTitle("Test Email");
            message.setContent("This is a test email sent from the EmailNotifier.");
            send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private Session getSession(String from,String password){
        try {
            if (session != null&&session.getStore().isConnected()){
                return session;
            }
        }catch (Exception e){
            e.printStackTrace();
            session=null;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        this.session=session;
        return session;
    }



    @Override
    public void destroy() {
        try {
            if (session != null) {
                session.getStore().close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session=null;
        }


    }

    @Override
    public String getName() {
        String s=getConfig().getFrom()+getConfig().getTo()+getConfig().getPassword();
        return  "enail:"+s.hashCode();
    }
}
