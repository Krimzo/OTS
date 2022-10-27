import rtos.SerialContainer;
import rtos.SerialObject;

public class Main {
    public static class Test implements SerialObject {
        Float3 position = new Float3(1, 2, 3);
        String name = null;
        boolean visible = true;
        char c = '6';

        @Override
        public void writeSerial(SerialContainer container) {
            container.saveObject("position", position);
            container.saveString("name", name);
            container.saveBool("visible", visible);
            container.saveChar("c", c);
        }

        @Override
        public void readSerial(SerialContainer container) {
            container.getObject("position", position);
            name = container.getString("name");
            visible = container.getBool("visible");
            c = container.getChar("c");
        }
    }

    public static void main(String[] args) {
        SerialContainer container = new SerialContainer();
        container.loadDataFromFile("test.txt");

        String name = container.getObject("test").getString("name");
        System.out.println(name);
    }
}
