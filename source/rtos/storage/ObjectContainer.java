package rtos.storage;

import rtos.processing.Formatter;
import rtos.processing.Parser;
import rtos.processing.Preprocessor;
import rtos.processing.SyntaxException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;

public final class ObjectContainer extends LinkedHashMap<String, Object> {
    public ObjectContainer() {}

    public void load(String data) throws SyntaxException {
        data = Preprocessor.processRawData(data);
        final Object object = Parser.parseProcessedData(data).get("");
        if (object instanceof ObjectContainer) {
            this.putAll((ObjectContainer) object);
        }
    }

    @Override
    public String toString() {
        return Formatter.formatObject(null, this, -1);
    }

    public void loadFromFile(String filepath) throws IOException, SyntaxException {
        this.load(Files.readString(Path.of(filepath)));
    }

    public void saveToFile(String filepath) throws IOException {
        Files.writeString(Path.of(filepath), this.toString());
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
