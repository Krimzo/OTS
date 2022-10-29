package rtos.standard;

public enum ScopeType {
    Primitive,
    Object,
    Array;

    public char opener() {
        return switch (this) {
            case Primitive -> '=';
            case Object -> '{';
            case Array -> '[';
        };
    }

    public char closer() {
        return switch (this) {
            case Primitive -> ';';
            case Object -> '}';
            case Array -> ']';
        };
    }
}
