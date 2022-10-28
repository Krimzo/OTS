package rtos.storage;

import rtos.processing.Formatter;
import rtos.processing.Parser;
import rtos.processing.Preprocessor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;

public final class ObjectContainer extends LinkedHashMap<String, Object> {
    public ObjectContainer() {}

    public void parse(String data) throws Exception {
        parse(data, true);
    }

    public void parse(String data, boolean preprocess) throws Exception {
        if (preprocess) {
            data = Preprocessor.process(data);
        }
        putAll(Parser.parse(data));
    }

    public String format() {
        return format(0);
    }

    public String format(int tabLevel) {
        return Formatter.format(this, tabLevel, false);
    }

    public void loadFromFile(String filepath) throws Exception {
        parse(Files.readString(Path.of(filepath)));
    }

    public void saveToFile(String filepath) throws Exception {
        Files.writeString(Path.of(filepath), format());
    }

    @Override
    public Object put(String name, Object object) {
        if (object instanceof StorableObject) {
            ObjectContainer container = new ObjectContainer();
            ((StorableObject) object).putToContainer(container);
            return super.put(name, container);
        }
        return super.put(name, object);
    }
}
