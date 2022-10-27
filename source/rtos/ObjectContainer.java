package rtos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ObjectContainer {
    final Map<String, Object> data = new LinkedHashMap<>();

    public ObjectContainer() {}

    // Data
    public void putFormattedData(String data) throws Exception {
        this.data.putAll(Parser.parse(Preprocessor.process(data)));
    }

    public String getFormattedData() {
        return Formatter.format(data, 0);
    }

    public void clearData() {
        data.clear();
    }

    // Files
    public void loadDataFromFile(String filepath) {
        try {
            putFormattedData(Files.readString(Path.of(filepath)));
        }
        catch (Exception ignored) {
            System.out.println("File \"" + filepath + "\" loading error");
        }
    }

    public void writeDataToFile(String filepath) {
        try {
            Files.writeString(Path.of(filepath), getFormattedData());
        }
        catch (Exception ignored) {
            System.out.println("File \"" + filepath + "\" writing error");
        }
    }

    // Putting
    public boolean put(String name, Object object) {
        if (Instance.isNull(object)) {
            data.put(name, null);
            return true;
        }
        if (Instance.isPrimitiveType(object.getClass())) {
            data.put(name, object);
            return true;
        }
        if (object instanceof StorableObject) {
            ObjectContainer container = new ObjectContainer();
            ((StorableObject) object).putToContainer(container);
            data.put(name, container);
            return true;
        }
        return false;
    }

    // Getting
    public <T> T getPrimitive(String name) {
        final Object object = data.get(name);
        if (!Instance.isNull(object) && Instance.isPrimitiveType(object.getClass())) {
            return (T) data.get(name);
        }
        return null;
    }

    public ObjectContainer getObject(String name) {
        final Object object = data.get(name);
        if (object instanceof ObjectContainer) {
            return (ObjectContainer) object;
        }
        return null;
    }

    public boolean getObject(String name, StorableObject outObject) {
        final ObjectContainer container = getObject(name);
        if (!Instance.isNull(container)) {
            outObject.getFromContainer(container);
            return true;
        }
        return false;
    }

    // Other
    public boolean contains(String name) {
        return data.containsKey(name);
    }

    public boolean remove(String name) {
        return !Instance.isNull(data.remove(name));
    }
}
