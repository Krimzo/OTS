package rtos.processing;

import rtos.standard.ScopeType;
import rtos.standard.Syntax;
import rtos.storage.ArrayContainer;
import rtos.storage.ObjectContainer;

import java.util.AbstractMap;
import java.util.LinkedHashMap;

public final class Parser {
    private Parser() {}

    public static ObjectContainer parseProcessedData(String data) {
        final ObjectContainer result = new ObjectContainer();
        for (var kv : extractKeysValues(data).entrySet()) {
            switch (kv.getValue().getKey()) {
                case Primitive -> {
                    final Object object = parsePrimitive(kv.getValue().getValue());
                    result.put(kv.getKey(), object);
                }
                case Object -> {
                    final ObjectContainer container = new ObjectContainer();
                    container.putAll(parseProcessedData(kv.getValue().getValue()));
                    result.put(kv.getKey(), container);
                }
                case Array -> {
                    final ArrayContainer container = parseArray(kv.getValue().getValue());
                    result.put(kv.getKey(), container);
                }
            }
        }
        return result;
    }

    private static LinkedHashMap<String, AbstractMap.SimpleEntry<ScopeType, String>> extractKeysValues(String data) {
        final LinkedHashMap<String, AbstractMap.SimpleEntry<ScopeType, String>> result = new LinkedHashMap<>();

        final StringBuilder keyBuilder = new StringBuilder();
        final StringBuilder valueBuilder = new StringBuilder();
        boolean readingKey = true;
        ScopeType scopeType = null;
        int scopeLevel = 0;

        for (char c : data.toCharArray()) {
            boolean isScopeDefiner = false;
            for (ScopeType scope : ScopeType.values()) {
                if (c == scope.opener()) {
                    if (scopeLevel == 0) {
                        readingKey = false;
                    }
                    else {
                        valueBuilder.append(c);
                    }

                    if (scopeType == null) {
                        scopeType = scope;
                    }
                    if (scopeType == scope) {
                        scopeLevel += 1;
                    }

                    isScopeDefiner = true;
                    break;
                }

                if (c == scope.closer()) {
                    if (scopeType == scope) {
                        scopeLevel -= 1;
                    }

                    if (scopeLevel == 0) {
                        final var value = new AbstractMap.SimpleEntry<>(scope, valueBuilder.toString());
                        result.put(keyBuilder.toString(), value);

                        keyBuilder.setLength(0);
                        valueBuilder.setLength(0);
                        readingKey = true;
                        scopeType = null;
                    }
                    else {
                        valueBuilder.append(c);
                    }

                    isScopeDefiner = true;
                    break;
                }
            }

            if (!isScopeDefiner) {
                (readingKey ? keyBuilder : valueBuilder).append(c);
            }
        }
        return result;
    }

    private static ArrayContainer parseArray(String data) {
        final ArrayContainer result = new ArrayContainer();

        final StringBuilder builder = new StringBuilder();
        ScopeType scopeType = null;
        int scopeLevel = 0;

        for (char c : data.toCharArray()) {
            if (c == Syntax.arraySplitter) {
                if (scopeLevel == 0) {
                    final String source = builder.toString();
                    final Object object = parsePrimitive(source);
                    if (object != null) {
                        result.add(object);
                    }
                    else {
                        result.add(parseProcessedData(source).get(""));
                    }
                    builder.setLength(0);
                }
                else {
                    builder.append(c);
                }
            }
            else {
                for (ScopeType scope : ScopeType.values()) {
                    if (c == scope.opener()) {
                        if (scopeType == null) {
                            scopeType = scope;
                        }
                        if (scopeType == scope) {
                            scopeLevel += 1;
                        }
                        break;
                    }

                    if (c == scope.closer()) {
                        if (scopeType == scope) {
                            scopeLevel -= 1;
                        }
                        if (scopeLevel == 0) {
                            scopeType = null;
                        }
                        break;
                    }
                }
                builder.append(c);
            }
        }
        return result;
    }

    private static Object parsePrimitive(String value) {
        try {
            if (value.equals("null")) {
                return null;
            }
            if (value.equals("true")) {
                return true;
            }
            if (value.equals("false")) {
                return false;
            }

            final char first = value.charAt(0);
            final char last = value.charAt(value.length() - 1);

            if (first == Syntax.stringWrapper && last == Syntax.stringWrapper) {
                return value.substring(1, value.length() - 1);
            }

            if (first == Syntax.charWrapper && last == Syntax.charWrapper) {
                return value.charAt(1);
            }

            if (value.chars().anyMatch(Character::isDigit)) {
                if (value.contains(".")) {
                    return Float.parseFloat(value);
                }
                return Integer.parseInt(value);
            }
        }
        catch (Exception ignored) {}
        return null;
    }
}
