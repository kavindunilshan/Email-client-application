import java.io.*;
import java.util.HashMap;
import java.util.List;

public class DiskOperations {

    // store in disk
    static void storeInHard(String filename, Object content) {
        try {
            FileOutputStream fis = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fis);

            oos.writeObject(content);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // load from disk
    static Object retainFromHard(String filename)
            throws IOException, ClassNotFoundException {

        HashMap<String, List<Email>> mails = new HashMap<>();

        try {
        ObjectInputStream ois = new ObjectInputStream
                (new FileInputStream(filename));

            return ois.readObject();
        } catch (FileNotFoundException exception) {
            return mails;
        }
    }
}