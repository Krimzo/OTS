package rtos.storage;

import java.util.ArrayList;

public final class ArrayContainer extends ArrayList<Object> {
    public ArrayContainer() {}

    @Override
    public boolean add(Object object) {
        if (object instanceof StorableObject) {
            ObjectContainer container = new ObjectContainer();
            ((StorableObject) object).putToContainer(container);
            return super.add(container);
        }
        return super.add(object);
    }
}
