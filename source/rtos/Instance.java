package rtos;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Instance {
    private Instance() {}

    private static final Set<Class<?>> PRIMITIVE_TYPES = new HashSet<>(Arrays.asList(
        Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, String.class
    ));

    public static boolean isPrimitiveType(Class<?> clazz) {
        return PRIMITIVE_TYPES.contains(clazz);
    }

    public static boolean isNull(Object object) {
        return object == null;
    }
}
