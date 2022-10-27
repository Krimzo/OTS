package rtos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public final class SerialContainer {
    private final Formatter formatter = new Formatter();
    private final Preprocessor preprocessor = new Preprocessor();
    private final Parser parser = new Parser();

    final Map<String, Object> data = new LinkedHashMap<>();

    public SerialContainer() {}

    // Data handling
    public void loadData(String data) throws Exception {
        data = preprocessor.process(data);
        this.data.putAll(parser.parse(data));
    }

    public String writeData() {
        return formatter.format(data, 0);
    }

    public void clearData() {
        data.clear();
    }

    // File handling
    public void loadDataFromFile(String filepath) {
        try {
            loadData(Files.readString(Path.of(filepath)));
        }
        catch (Exception ignored) {
            System.out.println("File \"" + filepath + "\" loading error");
        }
    }

    public void writeDataToFile(String filepath) {
        try {
            Files.writeString(Path.of(filepath), writeData());
        }
        catch (Exception ignored) {
            System.out.println("File \"" + filepath + "\" writing error");
        }
    }

    // Setters
    public void saveBool(String name, boolean value) {
        data.put(name, value);
    }

    public void saveLong(String name, long value) {
        data.put(name, value);
    }

    public void saveDouble(String name, double value) {
        data.put(name, value);
    }

    public void saveChar(String name, char value) {
        data.put(name, value);
    }

    public void saveString(String name, String value) {
        data.put(name, value);
    }

    public void saveObject(String name, SerialObject object) {
        SerialContainer container = new SerialContainer();
        object.writeSerial(container);
        data.put(name, container);
    }

    // Getters
    public boolean getBool(String name) {
        return (boolean) data.get(name);
    }

    public long getLong(String name) {
        return (long) data.get(name);
    }

    public double getDouble(String name) {
        return (double) data.get(name);
    }

    public char getChar(String name) {
        return (char) data.get(name);
    }

    public String getString(String name) {
        return (String) data.get(name);
    }

    public SerialContainer getObject(String name) {
        return (SerialContainer) data.get(name);
    }

    public <T extends SerialObject> void getObject(String name, T object) {
        SerialContainer container = getObject(name);
        if (container != null) {
            object.readSerial(container);
        }
    }

    // Full getters
    private <T> Map<String, T> getAll(Class<T> klass) {
        Map<String, T> result = new LinkedHashMap<>();
        for (var object : data.entrySet()) {
            final Object value = object.getValue();
            if (klass.isInstance(value)) {
                result.put(object.getKey(), (T) value);
            }
        }
        return result;
    }

    public Map<String, Boolean> getBools() {
        return getAll(Boolean.class);
    }

    public Map<String, Long> getLongs() {
        return getAll(Long.class);
    }

    public Map<String, Double> getDoubles() {
        return getAll(Double.class);
    }

    public Map<String, Character> getChars() {
        return getAll(Character.class);
    }

    public Map<String, String> getStrings() {
        return getAll(String.class);
    }

    public Map<String, SerialContainer> getObjects() {
        return getAll(SerialContainer.class);
    }
}
