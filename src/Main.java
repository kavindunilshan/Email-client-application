import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    // To store all the recipients
    // For sending mails for birthday
    // key: date   value: Person
    static HashMap<String, ArrayList<Person>> recipient = new HashMap<>();

    // To store mails for serialize each email object in disk
    // key: date   value: Email
    static HashMap<String, List<Email>> mails = new HashMap<>();

    static String currentDate;
    static int count;

    // format mail data to print
    static String mailData(Email email) {
        return String.format
                ("%s, %s, %s", email.getMail(),
                        email.getSubject(), email.getContent());
    }


    // Static initializer to initialize static objects
    static {
        currentDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        // load mails from disk
        try {
            mails = (HashMap<String, List<Email>>)DiskOperations.retainFromHard("mails.ser");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // load data from clientList.txt
        // create objects for each person in the file using factory method
        try {
            FileReader fr = new FileReader("clientList.txt");
            BufferedReader reader = new BufferedReader(fr);

            String newRecipient;
            while ((newRecipient = reader.readLine()) != null)
            {
                // splitting inputs
                String relation = newRecipient.split(":")[0];
                String details [] = newRecipient.split(" ")[1].split(",");


                // Using factory design pattern
                Person person = RecipientFactory.createRecipient(relation, details);
            }

            // closing files
            reader.close();
            fr.close();

        } catch (FileNotFoundException exception) {

        }catch (IOException exception) {
            exception.printStackTrace();
        }

        DiskOperations.storeInHard("mails.ser", mails);
    }

    public static void main(String[] args) throws InvalidInputException {
        Scanner sc = new Scanner(System.in);

        // Email Client is running
        while (true) {

            // load mails from disk
            try {
                mails = (HashMap<String, List<Email>>)DiskOperations.retainFromHard("mails.ser");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println("Enter option type: \n"
                    + "0 - Close the software\n"
                    + "1 - Adding a new recipient\n"
                    + "2 - Sending an email\n"
                    + "3 - Printing out all the recipients who have birthdays\n"
                    + "4 - Printing out details of all the emails sent\n"
                    + "5 - Printing out the number of recipient objects in the application");

            int option = sc.nextInt();
            sc.nextLine();


            // Email Client close the program
            if (option == 0)
                break;

            switch(option){
                case 1:
                    // input format - Official: nimal,nimal@mail.com,ceo
                    String newRecipient = sc.nextLine();

                    // splitting inputs
                    String relation = newRecipient.split(":")[0];
                    String details [] = newRecipient.split(" ")[1].split(",");

                    // Open clientList.txt and include newRecipient
                    try {
                        BufferedWriter writer = new BufferedWriter
                                (new FileWriter("clientList.txt", true));

                        writer.write(newRecipient+"\n");
                        writer.close();

                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    // Using factory design pattern

                    try {
                        Person person1 = RecipientFactory.createRecipient(relation, details);
                    }catch (Exception e) {

                    }
                    break;

                    // send custom email
                case 2:
                    // input format - email, subject, content
                    String[] newMailData = sc.nextLine().split(",");


                    // code to send an email
                    Email newMail = new Email(newMailData[0], newMailData[1],
                            newMailData[2]);

                    newMail.setWishMail(false);

                    newMail.send();

                    // Set the date the new mail send
                    newMail.setDate
                            (new SimpleDateFormat
                                    ("yyyy/MM/dd").format(new Date()));

                    newMail.store();
                    break;

                // Print all the recipients have birthday today
                case 3:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    String date = sc.next().substring(5);

                    if (recipient.containsKey(date)) {
                        var personList = recipient.get(date);

                        for (var person: personList)
                            System.out.println(person.getName() + " have birthday on " + date);

                        if (personList.isEmpty()) {
                            System.out.println("There are no birthdays today");
                        }
                    }
                    break;
                case 4:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    date = sc.next().substring(0,10);
                    // code to print the details of all the emails sent on the input date
                    if(mails.containsKey(date)) {
                        for (Email email: mails.get(date))
                            System.out.println(mailData(email));
                    } else{
                        System.out.println("There are no emails sent today");
                    }

                    break;

                    // Print recipient count
                case 5:
                    System.out.println(count);

                    break;

            }
            // serialize all the mail objects
            DiskOperations.storeInHard("mails.ser", mails);
        }

    }
}
