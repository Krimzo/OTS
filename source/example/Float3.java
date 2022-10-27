package example;

import rtos.ObjectContainer;
import rtos.StorableObject;

import java.util.Locale;

public class Float3 implements StorableObject {
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
    public void putToContainer(ObjectContainer container) {
        container.put("x", x);
        container.put("y", y);
        container.put("z", z);
    }

    @Override
    public void getFromContainer(ObjectContainer container) {
        x = container.getPrimitive("x");
        y = container.getPrimitive("y");
        z = container.getPrimitive("z");
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "(%.2f, %.2f, %.2f)", x, y, z);
    }
}
