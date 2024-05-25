package container

import language.Preprocessor
import utility.safe

interface DataContainer {
    fun fromString(data: String, preprocessor: Preprocessor = Preprocessor()): Boolean
    override fun toString(): String
}

fun Any.fromContainer(container: MapContainer): Boolean {
    for (field in this::class.java.declaredFields) {
        if (!field.trySetAccessible())
            continue

        safe {
            var value: Any? = container[field.name]
            if (value is ObjectContainer) {
                value = value.value
            }
            field.set(this, value)
        }
    }
    return true
}

fun Any.toContainer(): MapContainer {
    val container = MapContainer()
    for (field in this::class.java.declaredFields) {
        if (!field.trySetAccessible())
            continue

        val fieldValue = field.get(this)
        container[field.name] = if (fieldValue !is DataContainer) {
            val container = ObjectContainer()
            container.value = fieldValue
            container
        }
        else {
            fieldValue
        }
    }
    return container
}
