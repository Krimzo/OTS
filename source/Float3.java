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
    public void writeSerial(SerialContainer container) {
        container.saveDouble("x", x);
        container.saveDouble("y", y);
        container.saveDouble("z", z);
    }

    @Override
    public void readSerial(SerialContainer container) {
        x = (float) container.getDouble("x");
        y = (float) container.getDouble("y");
        z = (float) container.getDouble("z");
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "(%.2f, %.2f, %.2f)", x, y, z);
    }
}
