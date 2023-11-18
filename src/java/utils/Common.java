package utils;

public class Common {
    public static boolean contains(String[] arrays, String e) {
        if (arrays == null) {
            arrays = new String[0];
        }
        for (String a : arrays) {
            if (e.equals(a)) {
                return true;
            }
        }
        return false;
    }
}
