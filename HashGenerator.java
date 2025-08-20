package university.management.system;

import org.mindrot.jbcrypt.BCrypt;

public class HashGenerator {
    public static void main(String[] args) {
        String[] users = {"admin123", "teacher123", "student123"};
        for (String pass : users) {
            String hash = BCrypt.hashpw(pass, BCrypt.gensalt());
            System.out.println(pass + " -> " + hash);
        }
    }
}















