
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Email {
    private static String mailName = "INSERT YOUR MAIL";
    private static String mailPassword = "INSERT YOUR PW";
    private static String recipientEmail = "INSERT RECIPIENT MAIL";
    private List<Reise> MalleListe;

    public Email(List<Reise> malleListe) {
        this.MalleListe = malleListe;
        sendMalleMail();
    }

    private void sendMalleMail() {
        /*import javax.mail.*;
        import javax.mail.internet.AddressException;
        import javax.mail.internet.InternetAddress;
        import javax.mail.internet.MimeMessage;
        Properties props = System.getProperties();
        System.out.println("hello again");
		String host = "exchange.hs-regensburg.de";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", "*******");
        props.put("mail.smtp.password", mailPassword);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(mailName));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            String mailTitle = new Date().toString() + " - Malle-Hits auf Urlaubsguru!";
            String mailBody = "Mallorca-Liste11111: ";
            message.setSubject(mailTitle);

            for(Reise k : MalleListe) {
                int counter = 1;
                if(k.ReiseZiel.toLowerCase().contains("mallorca")) {
                    mailBody += ("\n" + counter++ + ". Angebot: " + k.ReisetoString());
                    System.out.println("\n" + counter++ + ". Angebot: " + k.ReisetoString());
                }
            }
            message.setText(mailBody);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, mailPassword, mailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public static String getMailName() {
        return mailName;
    }

    public static void setMailName(String mailName) {
        Email.mailName = mailName;
    }

    public static String getMailPassword() {
        return mailPassword;
    }

    public static void setMailPassword(String mailPassword) {
        Email.mailPassword = mailPassword;
    }

    public static String getRecipientEmail() {
        return recipientEmail;
    }

    public static void setRecipientEmail(String recipientEmail) {
        Email.recipientEmail = recipientEmail;
    }*/

    }
}