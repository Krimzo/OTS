package rtos.processing;

import rtos.standard.ScopeType;
import rtos.standard.Syntax;
import rtos.storage.ArrayContainer;
import rtos.storage.ObjectContainer;

public final class Formatter {
    private Formatter() {}

    public static String formatObject(String name, Object object, int dataTabLevel) {
        if (object instanceof ObjectContainer) {
            return formatObjectContainer(name, (ObjectContainer) object, dataTabLevel + 1);
        }
        if (object instanceof ArrayContainer) {
            return formatArrayContainer(name, (ArrayContainer) object, dataTabLevel + 1);
        }
        if (object instanceof String) {
            return formatString(name, (String) object, dataTabLevel);
        }
        if (object instanceof Character) {
            return formatChar(name, (Character) object, dataTabLevel);
        }
        return formatDefault(name, object, dataTabLevel);
    }

    private static String formatObjectContainer(String name, ObjectContainer container, int dataTabLevel) {
        final StringBuilder builder = new StringBuilder();

        builder.append(getTabs(dataTabLevel - 1));
        if (name != null) {
            builder.append(name);
            builder.append(" ");
        }
        builder.append(ScopeType.Object.opener());
        builder.append("\n");

        for (var object : container.entrySet()) {
            builder.append(formatObject(object.getKey(), object.getValue(), dataTabLevel));
            builder.append("\n");
        }

        builder.append(getTabs(dataTabLevel - 1));
        builder.append(ScopeType.Object.closer());
        return builder.toString();
    }

    private static String formatArrayContainer(String name, ArrayContainer container, int dataTabLevel) {
        final StringBuilder builder = new StringBuilder();

        builder.append(getTabs(dataTabLevel - 1));
        if (name != null) {
            builder.append(name);
            builder.append(" ");
        }
        builder.append(ScopeType.Array.opener());
        builder.append("\n");

        for (Object object : container) {
            builder.append(formatObject(null, object, dataTabLevel));
            builder.append(Syntax.arraySplitter);
            builder.append("\n");
        }

        builder.append(getTabs(dataTabLevel - 1));
        builder.append(ScopeType.Array.closer());
        return builder.toString();
    }

    private static String formatString(String name, String object, int dataTabLevel) {
        return formatPrimitive(name, object, dataTabLevel, String.valueOf(Syntax.stringWrapper));
    }

    private static String formatChar(String name, Character object, int dataTabLevel) {
        return formatPrimitive(name, object, dataTabLevel, String.valueOf(Syntax.charWrapper));
    }

    private static String formatDefault(String name, Object object, int dataTabLevel) {
        return formatPrimitive(name, object, dataTabLevel, "");
    }

    private static String formatPrimitive(String name, Object object, int dataTabLevel, String wrapper) {
        final StringBuilder builder = new StringBuilder();

        builder.append(getTabs(dataTabLevel));
        if (name != null) {
            builder.append(name);
            builder.append(" ");
            builder.append(ScopeType.Primitive.opener());
            builder.append(" ");
        }

        builder.append(wrapper);
        builder.append(object);
        builder.append(wrapper);

        if (name != null) {
            builder.append(ScopeType.Primitive.closer());
        }
        return builder.toString();
    }

    private static String getTabs(int tabLevel) {
        return "    ".repeat(Math.max(tabLevel, 0));
    }
}
