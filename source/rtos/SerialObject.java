package rtos;

public interface SerialObject {
    void writeSerial(SerialContainer container);
    void readSerial(SerialContainer container);
}
