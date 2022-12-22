import container.DataContainer

interface StorableObject {
    fun toContainer(): DataContainer
    fun fromContainer(container: DataContainer): Boolean
}
