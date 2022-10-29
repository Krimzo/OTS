package example;

import rtos.standard.ScopeType;
import rtos.storage.ArrayContainer;
import rtos.storage.ObjectContainer;

public class Main {
    private static final String filepath = "example.txt";

    public static void main(String[] args) throws Exception {
        saveExample();
        loadExample();
    }

    private static void saveExample() throws Exception {
        ObjectContainer container = new ObjectContainer();

        container.put("someBool", true);
        container.put("someInt", 6);
        container.put("someFloat", 12.0f);
        container.put("someChar", 'A');
        container.put("someString", "yey");
        container.put("someEnum", ScopeType.Primitive);
        container.put("someRef", new Main());
        container.put("someNullRef", null);
        container.put("someStorableObject", new Float3(1, 2, 3));

        ArrayContainer someMixedArray = new ArrayContainer();
        someMixedArray.add(0);
        someMixedArray.add(1.0f);
        someMixedArray.add('d');
        someMixedArray.add("cool");
        container.put("someMixedArray", someMixedArray);

        ArrayContainer someMatrix = new ArrayContainer();
        for (int i = 0; i < 3; i++) {
            ArrayContainer tempArrayContainer = new ArrayContainer();
            for (int j = 0; j < 3; j++) {
                tempArrayContainer.add(i);
            }
            someMatrix.add(tempArrayContainer);
        }
        container.put("someMatrix", someMatrix);

        container.saveToFile(filepath);
    }

    private static void loadExample() throws Exception {
        ObjectContainer container = new ObjectContainer();
        container.loadFromFile(filepath);
        System.out.println(container);
    }
}
