package rtos;

import java.util.Map;

public final class Formatter {
    private Formatter() {}

    public static String format(Map<String, Object> data, int tabLevel) {
        StringBuilder builder = new StringBuilder();
        final String tabs = "    ".repeat(tabLevel);

        for (var object : data.entrySet()) {
            final Object value = object.getValue();
            final Class<?> valueClass = (value != null) ? value.getClass() : null;

            builder.append(tabs);
            builder.append(object.getKey());
            builder.append(" ");

            if (valueClass == SerialContainer.class) {
                SerialContainer container = (SerialContainer) value;

                builder.append("{\n");
                builder.append(format(container.data, tabLevel + 1));
                builder.append(tabs);
                builder.append("}\n");
            }
            else {
                builder.append("= ");
                if (valueClass == Character.class) {
                    builder.append("'");
                    builder.append(value);
                    builder.append("'");
                }
                else if (valueClass == String.class) {
                    builder.append('"');
                    builder.append(value);
                    builder.append('"');
                }
                else {
                    builder.append(value);
                }
                builder.append(";\n");
            }
        }
        return builder.toString();
    }
}
