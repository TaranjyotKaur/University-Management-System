package university.management.system;

import org.mindrot.jbcrypt.BCrypt;

public class TestBCrypt {
    public static void main(String[] args) {
        // Plain text password
        String password = "MySecurePassword123!";

        // Hash the password with BCrypt (12 rounds of salt)
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        System.out.println("Hashed password: " + hashed);

        // Now check if the plain password matches the hash
        boolean match = BCrypt.checkpw(password, hashed);
        System.out.println("Password Match? " + match);

        // Try a wrong password
        boolean wrongMatch = BCrypt.checkpw("WrongPassword", hashed);
        System.out.println("Wrong password Match? " + wrongMatch);
    }
}
