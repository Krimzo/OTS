import container.DataContainer
import container.MapContainer
import container.ObjectContainer
import utility.safe

interface StorableObject {
    fun toContainer(): DataContainer {
        val container = MapContainer()
        for (field in this::class.java.declaredFields) {
            if (field.trySetAccessible()) {
                field.get(this).let {
                    container[field.name] = if (it is DataContainer) it else ObjectContainer(it)
                }
            }
        }
        return container
    }

    fun fromContainer(container: DataContainer): Boolean {
        if (container !is MapContainer) {
            return false
        }

        for (field in this::class.java.declaredFields) {
            if (field.trySetAccessible()) {
                safe {
                    var value: Any? = container[field.name]
                    if (value is ObjectContainer) {
                        value = value.value
                    }
                    field.set(this, value)
                }
            }
        }
        return true
    }
}
