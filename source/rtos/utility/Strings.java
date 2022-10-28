package rtos.utility;

public final class Strings {
    private Strings() {}

    public static int count(String data, char value) {
        int counter = 0;
        for (char c : data.toCharArray()) {
            if (c == value) {
                counter += 1;
            }
        }
        return counter;
    }

    public static boolean hasNumber(String value) {
        for (char c : value.toCharArray()) {
            if (c > 47 && c < 58) {
                return true;
            }
        }
        return false;
    }
}
