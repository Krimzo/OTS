import container.DataContainer
import container.MapContainer
import container.ObjectContainer
import utility.safe

class Float3 : StorableObject {
    var x: Float
    var y: Float
    var z: Float

    constructor() : this(0f)

    constructor(a: Float) : this(a, a, a)

    constructor(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    override fun toContainer(): DataContainer {
        val result = MapContainer()
        result["x"] = ObjectContainer(x)
        result["y"] = ObjectContainer(y)
        result["z"] = ObjectContainer(z)
        return result
    }

    override fun fromContainer(container: DataContainer): Boolean {
        if (container is MapContainer) {
            safe { x = (container["x"] as ObjectContainer).value as Float }
            safe { y = (container["y"] as ObjectContainer).value as Float }
            safe { z = (container["z"] as ObjectContainer).value as Float }
            return true
        }
        return false
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }
}