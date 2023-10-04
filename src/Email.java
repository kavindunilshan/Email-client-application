import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class Email implements Serializable {
    private String mail;
    protected String subject;
    protected String content;
    private String date;
    private boolean isWishMail;

    protected Email(String mail, String date) {
        this.mail = mail;
        this.date = date;
    }

    public Email(String mail, String subject, String content) {
        this.mail = mail;
        this.subject = subject;
        this.content = content;
    }

    public boolean getwishMail() {
        return isWishMail;
    }

    public void setWishMail(boolean wishMail) {
        isWishMail = wishMail;
    }

    @Override
    public String toString() {
        return "Email{" +
                "mail='" + mail + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", isWishMail=" + isWishMail +
                '}';
    }

    public String getMail() {
        return mail;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Get email instance depending on wish person type
    public static   Email getWishInstance(Person person) {
        String relation = person.getRelation();
        String mail = person.getMail();
        String birthday = person.getBirthday();

        if (relation.equals("Official_friend"))
            return new WishEmailOfficeFriend(mail, birthday);
        else if (relation.equals("Friend")) {
            return new WishEmailFriend(mail, birthday);
        } else
            return null;

    }

    public CompletableFuture<Void> sendAsync() {
        return CompletableFuture.runAsync(() -> {
            int a = 0;
            if (Main.mails.containsKey(Main.currentDate) && isWishMail) {
                List<Email> sents = Main.mails.get(Main.currentDate);
                for (Email email : sents) {
                    if(email.getMail().equals(mail) && email.getwishMail()) {
                        a = 1;
                    }
                }
            }
            if (a == 1) {
                return;
            }
            send();
        });
    }

    // sending an email
    void send(){

        final String username = "kavinilj10@gmail.com";
        final String password = "qfshespookvyxpor";

        Properties prop = new Properties();

        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("kavinilj10@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mail));

            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Successfully sent an email to " + mail);
            date = Main.currentDate;

            store();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    // store in hashmap use key as date and value as email
    void store() {
        if (Main.mails.containsKey(date)) {
            List<Email> sents = Main.mails.get(date);
            sents.add(this);
            Main.mails.put(date, sents);
        } else {
            List<Email> sents = new ArrayList<>();
            sents.add(this);
            Main.mails.put(date, sents);
        }
    }

}



class WishEmailOfficeFriend extends Email {
    final private String subject = "Happy Birthday";
    final private String content = "Wish you a Happy Birthday \n Kavindu Nilshan";

    public WishEmailOfficeFriend(String mail, String date) {
        super(mail, date);
        super.subject = subject;
        super.content = content;
    }
}


class WishEmailFriend extends Email {
    final private String subject = "Happy Birthday";
    final private String content = "Hugs and love on your birthday. \n Kavindu Nilshan";

    public WishEmailFriend(String mail, String date) {
        super(mail, date);
        super.subject = subject;
        super.content = content;
    }
}
