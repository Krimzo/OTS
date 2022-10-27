package rtos;

public interface StorableObject {
    void putToContainer(ObjectContainer container);
    void getFromContainer(ObjectContainer container);
}
