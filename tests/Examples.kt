import container.MapContainer
import container.fromContainer
import container.toContainer

class SomeData {
    val id: String = ""
    val value: Long? = null

    override fun toString(): String {
        return "SomeData($id, $value)"
    }
}

fun main() {
    // From string
    val data = SomeData()
    data.fromContainer(MapContainer("""
        {
            id: "some_id",
            value: 16,
        }
        """.trimIndent()))
    println(data) // SomeData(some_id, 16)

    // To string
    val container = data.toContainer()
    println(container) // { id: "some_id", value: 16 }
}
