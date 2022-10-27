package rtos;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Parser {
    public Parser() {}

    private boolean containsNumber(String value) {
        for (char c : value.toCharArray()) {
            if (c > 47 && c < 58) {
                return true;
            }
        }
        return false;
    }

    private Object processValue(String value) {
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
                return Double.parseDouble(value);
            }
            return Long.parseLong(value);
        }

        return Boolean.parseBoolean(value);
    }

    public Map<String, Object> parse(String data) {
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
                        SerialContainer container = new SerialContainer();
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
                        result.put(key, processValue(builder.toString()));
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
}
