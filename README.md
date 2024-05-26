## About
JSON-like object serialisation for Kotlin objects.

## Example
```kotlin
import container.*

class Data0 : MapSerializable {
    var id: String = ""
    var value: Int = 0

    override fun toMap(map: MapContainer) {
        map["id"] = ObjectContainer.from(id)
        map["value"] = ObjectContainer.from(value)
    }

    override fun fromMap(map: MapContainer) {
        id = map["id"]?.getString() ?: ""
        value = map["value"]?.getInt() ?: 0
    }

    override fun toString(): String {
        return "Data0($id, $value)"
    }
}

class Data1 : MapSerializable {
    var id: String = ""
    var data0: Data0 = Data0()
    var chance: Float? = 0f

    override fun toMap(map: MapContainer) {
        map["id"] = ObjectContainer.from(id)
        map["data0"] = data0.toContainer()
        map["chance"] = ObjectContainer.from(chance)
    }

    override fun fromMap(map: MapContainer) {
        id = map["id"]?.getString() ?: ""
        data0.fromContainer(map["data0"])
        chance = map["chance"]?.getFloat()
    }

    override fun toString(): String {
        return "Data1($id, $data0, $chance)"
    }
}

fun main() {
    // From string
    val data = Data1()
    data.fromContainer(MapContainer("""
        {
            id: "some_id1",
            data0: {
                id: "some_id0",
                value: 16
            },
            chance: 0.5
        }
        """.trimIndent()))
    println(data) // Data1(some_id1, Data0(some_id0, 16), 0.5)

    // To string
    val container = data.toContainer()
    println(container) // { id: "some_id1", data0: { id: "some_id0", value: 16 }, chance: 0.5 }
}
```
