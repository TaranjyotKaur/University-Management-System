package university.management.system;

public class Validation {

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(regex);
    }

    public static boolean isValidPhone(String phone) {
        String regex = "^[0-9]{10}$";
        return phone != null && phone.matches(regex);
    }

    public static boolean isValidRoll(String roll) {
        String regex = "^[A-Z0-9]{6,10}$";
        return roll != null && roll.matches(regex);
    }

    public static boolean isValidMarks(int marks) {
        return marks >= 0 && marks <= 100;
    }

    public static boolean isValidUsername(String username) {
        String regex = "^[a-zA-Z0-9._-]{3,20}$";
        return username != null && username.matches(regex);
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}
