package rtos;

public interface SerialObject {
    void writeToContainer(SerialContainer container);
    void readFromContainer(SerialContainer container);
}
