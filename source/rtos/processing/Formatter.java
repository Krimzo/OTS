package rtos.processing;

import rtos.storage.ArrayContainer;
import rtos.storage.ObjectContainer;

import java.util.Map;

public final class Formatter {
    private Formatter() {}

    private static String getTabs(int tabLevel) {
        return "    ".repeat(tabLevel);
    }

    public static String format(Map<String, Object> data, int tabLevel, boolean inArray) {
        StringBuilder builder = new StringBuilder();
        for (var object : data.entrySet()) {
            formatKey(builder, object.getKey(), tabLevel, inArray);
            formatValue(builder, object.getValue(), tabLevel, inArray);
        }
        return builder.toString();
    }

    private static void formatKey(StringBuilder builder, String key, int tabLevel, boolean inArray) {
        if (!inArray) {
            builder.append(getTabs(tabLevel)).append(key).append(" ");
        }
    }

    private static void formatValue(StringBuilder builder, Object value, int tabLevel, boolean inArray) {
        if (value instanceof ArrayContainer) {
            formatArray(builder, (ArrayContainer) value, tabLevel, inArray);
        }
        else if (value instanceof ObjectContainer) {
            formatObject(builder, (ObjectContainer) value, tabLevel, inArray);
        }
        else if (value instanceof String) {
            formatString(builder, (String) value, inArray);
        }
        else if (value instanceof Character) {
            formatChar(builder, (Character) value, inArray);
        }
        else {
            formatDefault(builder, value, inArray);
        }
    }

    private static void formatArray(StringBuilder builder, ArrayContainer container, int tabLevel, boolean inArray) {
        if (container.size() == 0) {
            builder.append("[]\n");
            return;
        }

        builder.append("[\n");
        for (Object object : container) {
            builder.append(getTabs(tabLevel + 1));
            formatValue(builder, object, tabLevel + 1, true);
            builder.append(",\n");
        }

        builder.append(getTabs(tabLevel));
        builder.append("]");

        if (!inArray) {
            builder.append("\n");
        }
    }

    private static void formatObject(StringBuilder builder, ObjectContainer container, int tabLevel, boolean inArray) {
        if (inArray) {
            builder.append("{\n");
            builder.append(container.format(tabLevel + 1));
            builder.append(getTabs(tabLevel));
            builder.append("}");
        }
        else {
            builder.append("{\n");
            builder.append(container.format(tabLevel + 1));
            builder.append(getTabs(tabLevel));
            builder.append("}\n");
        }
    }

    private static void formatString(StringBuilder builder, String value, boolean inArray) {
        if (inArray) {
            builder.append('"');
            builder.append(value);
            builder.append('"');
        }
        else {
            builder.append("= ");
            builder.append('"');
            builder.append(value);
            builder.append('"');
            builder.append(";\n");
        }
    }

    private static void formatChar(StringBuilder builder, Character value, boolean inArray) {
        if (inArray) {
            builder.append("'");
            builder.append(value);
            builder.append("'");
        }
        else {
            builder.append("= ");
            builder.append("'");
            builder.append(value);
            builder.append("'");
            builder.append(";\n");
        }
    }

    private static void formatDefault(StringBuilder builder, Object value, boolean inArray) {
        if (inArray) {
            builder.append(value);
        }
        else {
            builder.append("= ");
            builder.append(value);
            builder.append(";\n");
        }
    }
}
