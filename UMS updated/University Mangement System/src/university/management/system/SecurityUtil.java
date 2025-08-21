package university.management.system;

import java.util.regex.Pattern;

public final class SecurityUtil {
    private SecurityUtil() {}

    private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9_.-]{3,32}$");
    private static final Pattern EMAIL    = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE    = Pattern.compile("^[0-9]{7,15}$");
    private static final Pattern ROLLNO   = Pattern.compile("^[A-Za-z0-9-]{3,20}$");

    // At least 8 chars incl. letter & digit (tune as you like)
    public static boolean strongPassword(char[] pw) {
        if (pw == null || pw.length < 8) return false;
        boolean hasL = false, hasD = false;
        for (char c : pw) {
            if (Character.isLetter(c)) hasL = true;
            if (Character.isDigit(c))  hasD = true;
        }
        return hasL && hasD;
    }

    public static boolean validUsername(String s) { return s != null && USERNAME.matcher(s).matches(); }
    public static boolean validEmail(String s)    { return s != null && EMAIL.matcher(s).matches(); }
    public static boolean validPhone(String s)    { return s != null && PHONE.matcher(s).matches(); }
    public static boolean validRoll(String s)     { return s != null && ROLLNO.matcher(s).matches(); }

    public static String maskRoll(String roll) {
        if (roll == null || roll.length() <= 2) return "**";
        return "****" + roll.substring(roll.length() - 2);
    }

    public static String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }
}
