package rtos;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Parser {
    private Parser() {}

    public static Map<String, Object> parse(String data) {
        Map<String, Object> result = new LinkedHashMap<>();

        StringBuilder builder = new StringBuilder();
        int objectLevel = 0;
        String key = "";

        for (char c : data.toCharArray()) {
            switch (c) {
                case '{' -> {
                    if (objectLevel == 0) {
                        key = builder.toString();
                        builder = new StringBuilder();
                    }
                    else {
                        builder.append(c);
                    }
                    objectLevel += 1;
                }
                case '}' -> {
                    objectLevel -= 1;
                    if (objectLevel == 0) {
                        ObjectContainer container = new ObjectContainer();
                        container.data.putAll(parse(builder.toString()));
                        result.put(key, container);
                        builder = new StringBuilder();
                    }
                    else {
                        builder.append(c);
                    }
                }
                case '=' -> {
                    if (objectLevel == 0) {
                        key = builder.toString();
                        builder = new StringBuilder();
                    }
                    else {
                        builder.append(c);
                    }
                }
                case ';' -> {
                    if (objectLevel == 0) {
                        result.put(key, parseValue(builder.toString()));
                        builder = new StringBuilder();
                    }
                    else {
                        builder.append(c);
                    }
                }
                default -> {
                    builder.append(c);
                }
            }
        }

        return result;
    }

    private static Object parseValue(String value) {
        if (value.equals("null")) {
            return null;
        }

        if (value.contains("\"")) {
            return value.replace("\"", "");
        }

        if (value.contains("'")) {
            return value.replace("'", "").charAt(0);
        }

        if (containsNumber(value)) {
            if (value.contains(".")) {
                return Float.parseFloat(value);
            }
            return Integer.parseInt(value);
        }

        return Boolean.parseBoolean(value);
    }

    private static boolean containsNumber(String value) {
        for (char c : value.toCharArray()) {
            if (c > 47 && c < 58) {
                return true;
            }
        }
        return false;
    }
}
