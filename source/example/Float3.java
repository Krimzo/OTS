package example;

import rtos.SerialContainer;
import rtos.SerialObject;

import java.util.Locale;

public class Float3 implements SerialObject {
    public float x = 0.0f;
    public float y = 0.0f;
    public float z = 0.0f;

    public Float3() {}

    public Float3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void writeToContainer(SerialContainer container) {
        container.put("x", x);
        container.put("y", y);
        container.put("z", z);
    }

    @Override
    public void readFromContainer(SerialContainer container) {
        x = container.getPrimitive("x");
        y = container.getPrimitive("y");
        z = container.getPrimitive("z");
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "(%.2f, %.2f, %.2f)", x, y, z);
    }
}
