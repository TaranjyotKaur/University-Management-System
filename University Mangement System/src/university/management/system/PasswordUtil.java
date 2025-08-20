package university.management.system;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash password with BCrypt
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
    }

    // Verify entered password against stored hash
    public static boolean checkPassword(String plainTextPassword, String storedHash) {
        if (storedHash == null || !storedHash.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hash");
        }
        return BCrypt.checkpw(plainTextPassword, storedHash);
    }
}
