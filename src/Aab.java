import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class A {
    int a = 12;

    static Object getA() {
        return null;
    }

}

class B extends A {
    int a = 10;
}

public class Aab {
    public static void main(String[] args) {
//        try {
//            FileWriter writer = new FileWriter
//                    ("clien.txt", true);
//
//            BufferedWriter writer1 = new BufferedWriter(writer);
//            writer1.write("Hello everyone");
//
//            writer1.close();
//
//        } catch (IOException excp) {
//            excp.printStackTrace();
//        }

        A name = (A)A.getA();
        System.out.println(name);
    }

}


