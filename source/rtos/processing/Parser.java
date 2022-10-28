package rtos.processing;

import rtos.storage.ArrayContainer;
import rtos.storage.ObjectContainer;
import rtos.utility.Instance;
import rtos.utility.Strings;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Parser {
    private Parser() {}

    public static Map<String, Object> parse(String data) throws Exception {
        runChecks(data);

        final Map<String, Object> result = new LinkedHashMap<>();
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        boolean readingKey = true;
        int scopeLevel = 0;
        int scopeType = 0;

        for (char c : data.toCharArray()) {
            switch (c) {
                case '=' -> {
                    if (scopeLevel == 0) {
                        readingKey = false;
                    }
                    else {
                        valueBuilder.append(c);
                    }
                }
                case ';' -> {
                    if (scopeLevel == 0) {
                        Object object = parseValue(valueBuilder.toString());
                        result.put(keyBuilder.toString(), object);

                        keyBuilder = new StringBuilder();
                        valueBuilder = new StringBuilder();
                        readingKey = true;
                    }
                    else {
                        valueBuilder.append(c);
                    }
                }

                case '{' -> {
                    if (scopeLevel == 0) {
                        readingKey = false;
                    }
                    else {
                        valueBuilder.append(c);
                    }

                    if (scopeType == 0) {
                        scopeType = 1;
                    }
                    if (scopeType == 1) {
                        scopeLevel += 1;
                    }
                }
                case '}' -> {
                    if (scopeType == 1) {
                        scopeLevel -= 1;
                    }

                    if (scopeLevel == 0) {
                        ObjectContainer container = new ObjectContainer();
                        container.parse(valueBuilder.toString());
                        result.put(keyBuilder.toString(), container);

                        keyBuilder = new StringBuilder();
                        valueBuilder = new StringBuilder();
                        readingKey = true;
                        scopeType = 0;
                    }
                    else {
                        valueBuilder.append(c);
                    }
                }

                case '[' -> {
                    if (scopeLevel == 0) {
                        readingKey = false;
                    }
                    else {
                        valueBuilder.append(c);
                    }

                    if (scopeType == 0) {
                        scopeType = 2;
                    }
                    if (scopeType == 2) {
                        scopeLevel += 1;
                    }
                }
                case ']' -> {
                    if (scopeType == 2) {
                        scopeLevel -= 1;
                    }

                    if (scopeLevel == 0) {
                        ArrayContainer container = parseArray(valueBuilder.toString());
                        result.put(keyBuilder.toString(), container);

                        keyBuilder = new StringBuilder();
                        valueBuilder = new StringBuilder();
                        readingKey = true;
                        scopeType = 0;
                    }
                    else {
                        valueBuilder.append(c);
                    }
                }

                default -> {
                    if (readingKey) {
                        keyBuilder.append(c);
                    }
                    else {
                        valueBuilder.append(c);
                    }
                }
            }
        }

        return result;
    }

    private static void runChecks(String data) throws Exception {
        if (Strings.count(data, '=') != Strings.count(data, ';')) {
            throw new Exception("Bad primitive initialisation");
        }
        if (Strings.count(data, '{') != Strings.count(data, '}')) {
            throw new Exception("Bad object initialisation");
        }
        if (Strings.count(data, '[') != Strings.count(data, ']')) {
            throw new Exception("Bad array initialisation");
        }
    }

    private static Object parseValue(String value) {
        try {
            if (value.equals("null")) {
                return null;
            }

            final char first = value.charAt(0);
            final char last = value.charAt(value.length() - 1);

            if (first == '"' && last == '"') {
                return value.substring(1, value.length() - 2);
            }

            if (first == '\'' && last == '\'') {
                return value.charAt(1);
            }

            if (Strings.hasNumber(value)) {
                if (value.contains(".")) {
                    return Float.parseFloat(value);
                }
                return Integer.parseInt(value);
            }

            return Boolean.parseBoolean(value);
        }
        catch (Exception ignored) {
            return null;
        }
    }

    private static ArrayContainer parseArray(String data) throws Exception {
        final ArrayContainer result = new ArrayContainer();

        StringBuilder builder = new StringBuilder();
        int scopeLevel = 0;
        int scopeType = 0;

        for (char c : data.toCharArray()) {
            switch (c) {
                case ',' -> {
                    if (scopeLevel == 0) {
                        final String source = builder.toString();
                        final Object object = parseValue(source);

                        if (!Instance.isNull(object)) {
                            result.add(object);
                        }
                        else {
                            result.add(parse(source).get(""));
                        }

                        builder = new StringBuilder();
                    }
                    else {
                        builder.append(c);
                    }
                }

                case '{' -> {
                    builder.append(c);
                    if (scopeType == 0) {
                        scopeType = 1;
                    }
                    if (scopeType == 1) {
                        scopeLevel += 1;
                    }
                }
                case '}' -> {
                    builder.append(c);
                    if (scopeType == 1) {
                        scopeLevel -= 1;
                    }
                    if (scopeLevel == 0) {
                        scopeType = 0;
                    }
                }

                case '[' -> {
                    builder.append(c);
                    if (scopeType == 0) {
                        scopeType = 2;
                    }
                    if (scopeType == 2) {
                        scopeLevel += 1;
                    }
                }
                case ']' -> {
                    builder.append(c);
                    if (scopeType == 2) {
                        scopeLevel -= 1;
                    }
                    if (scopeLevel == 0) {
                        scopeType = 0;
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
