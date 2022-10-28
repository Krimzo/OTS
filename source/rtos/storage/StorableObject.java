package rtos.storage;

public interface StorableObject {
    void putToContainer(ObjectContainer container);
    void getFromContainer(ObjectContainer container);
}
