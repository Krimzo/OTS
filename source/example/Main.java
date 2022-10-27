package example;

import rtos.SerialContainer;
import rtos.SerialObject;

public class Main implements SerialObject {
    private final Float3 position = new Float3(1, 2, 3);
    private String name = null;
    private boolean visible = true;
    private char c = '9';

    public static void main(String[] args) {
        SerialContainer container = new SerialContainer();
        container.put("main", new Main());
        container.writeDataToFile("test.txt");

        Float3 data = new Float3();
        container.clearData();
        container.loadDataFromFile("test.txt");
        if (container.getObject("main").getObject("position", data)) {
            System.out.println(data);
        }
    }

    @Override
    public void writeToContainer(SerialContainer container) {
        container.put("position", position);
        container.put("name", name);
        container.put("visible", visible);
        container.put("c", c);
    }

    @Override
    public void readFromContainer(SerialContainer container) {
        container.getObject("position", position);
        name = container.getPrimitive("name");
        visible = container.getPrimitive("visible");
        c = container.getPrimitive("c");
    }
}
