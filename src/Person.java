import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// Factory method design pattern Product
public interface Person extends Serializable {
    String getName();
    String getMail();
    String getRelation();
    String getBirthday();

    void store();


}


class OfficialRecipient implements Person{
    String name;
    String mail;
    String designation;
    String relation = "Official";

    public OfficialRecipient(String details []) {
        this.name = details[0];
        this.mail = details[1];
        this.designation = details[2];
        Main.count += 1;
        store();

    }

    @Override
    public void store() {
        if (Main.recipient.containsKey("Official"))
            Main.recipient.get("Official").add(this);
        else
            Main.recipient.put("Official", new ArrayList<>(Arrays.asList(this)));

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public String getRelation() {
        return relation;
    }

    @Override
    public String getBirthday() {
        return null;
    }
}

class OfficialFriendRecipient implements Person{
    String name;
    String mail;
    String designation;
    String birthday;
    String relation = "Official_friend";

    public OfficialFriendRecipient(String details []) {
        this.name = details[0];
        this.mail = details[1];
        this.designation = details[2];
        this.birthday = details[3];
        Main.count += 1;
        store();

        // Send email to person who have birthday today
        if (Main.currentDate.substring(5).equals(birthday.substring(5))) {
            Email wish = Email.getWishInstance(this);
            wish.setWishMail(true);
            CompletableFuture<Void> sendFuture = wish.sendAsync();
            wish.setDate(Main.currentDate);
        }

    }



    @Override
    public void store() {
        String date = birthday.substring(5);
        if (Main.recipient.containsKey(date)) {
            Main.recipient.get(date);
        } else {
            Main.recipient.put(date, new ArrayList<>(Arrays.asList(this)));
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public String getRelation() {
        return relation;
    }

    @Override
    public String getBirthday() {
        return birthday;
    }

}

class FriendRecipient implements Person {
    String name;
    String nickname;
    String mail;
    String birthday;
    String relation = "Friend";

    public FriendRecipient(String details []) {
        this.name = details[0];
        this.nickname = details[1];
        this.mail = details[2];
        this.birthday = details[3];
        Main.count += 1;
        store();


        // Send email to person who have birthday today
        if (Main.currentDate.substring(5).equals(birthday.substring(5))) {
            Email wish = Email.getWishInstance(this);
            wish.setWishMail(true);
            CompletableFuture<Void> sendFuture = wish.sendAsync();
            wish.setDate(Main.currentDate);

//            DiskOperations.storeInHard("mails.ser", Main.mails);
        }
    }


    @Override
    public void store() {
        String date = birthday.substring(5);
        if (Main.recipient.containsKey(date)) {
            ArrayList<Person> persons = Main.recipient.get(date);
            persons.add(this);
            Main.recipient.put(date, persons);
        } else {
            Main.recipient.put(date, new ArrayList<>(Arrays.asList(this)));
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public String getRelation() {
        return relation;
    }

    @Override
    public String getBirthday() {
        return birthday;
    }

}
