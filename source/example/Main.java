package example;

import rtos.storage.ArrayContainer;
import rtos.storage.ObjectContainer;
import rtos.storage.StorableObject;

public class Main implements StorableObject {
    private final Float3 position = new Float3(1, 2, 3);
    private String name = null;
    private boolean visible = true;
    private char c = '9';
    private int[] data = new int[10];

    public static void main(String[] args) throws Exception {
        ObjectContainer container = new ObjectContainer();
        container.put("main", new Main());
        container.saveToFile("test.txt");

        container.clear();
        container.loadFromFile("test.txt");
        System.out.println(container.format());
    }

    @Override
    public void putToContainer(ObjectContainer container) {
        container.put("position", position);
        container.put("name", name);
        container.put("visible", visible);
        container.put("c", c);

        ArrayContainer data = new ArrayContainer();
        for (int val : this.data) data.add(val);
        container.put("data", data);
    }

    @Override
    public void getFromContainer(ObjectContainer container) {
        position.getFromContainer((ObjectContainer) container.get("position"));
        name = (String) container.get("name");
        visible = (boolean) container.get("visible");
        c = (char) container.get("c");

        ArrayContainer data = (ArrayContainer) container.get("data");
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = (int) data.get(i);
        }
    }
}
