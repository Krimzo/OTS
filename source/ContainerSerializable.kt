import container.ArrayContainer
import container.DataContainer
import container.MapContainer

interface ContainerSerializable {
    fun toContainer(): DataContainer
    fun fromContainer(container: DataContainer?)
}

interface MapSerializable : ContainerSerializable {
    fun toMap(map: MapContainer)
    fun fromMap(map: MapContainer)

    override fun toContainer(): DataContainer {
        val container = MapContainer()
        toMap(container)
        return container
    }
    override fun fromContainer(container: DataContainer?) {
        if (container !is MapContainer) return
        fromMap(container)
    }
}

interface ArraySerializable : ContainerSerializable {
    fun toArray(array: ArrayContainer)
    fun fromArray(array: ArrayContainer)

    override fun toContainer(): DataContainer {
        val container = ArrayContainer()
        toArray(container)
        return container
    }
    override fun fromContainer(container: DataContainer?) {
        if (container !is ArrayContainer) return
        fromArray(container)
    }
}
