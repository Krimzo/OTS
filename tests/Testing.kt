import container.*

class Person : MapSerializable {
    var name: String = ""
    var age: Float? = 0f

    constructor() {
    }

    constructor(name: String, age: Float?) {
        this.name = name
        this.age = age
    }

    override fun fromMap(map: MapContainer) {
        this.name = map["name"]?.getString() ?: ""
        this.age = map["age"]?.getFloat()
    }

    override fun toMap(map: MapContainer) {
        map["name"] = LiteralContainer.from(name)
        map["age"] = LiteralContainer.from(age)
    }

    override fun toString(): String {
        return "Person($name, $age)"
    }
}

fun main() {
    val test = { container: Any, expected: String ->
        val result = container.toString()
        if (result != expected) {
            throw Exception("Expected: $expected, but got: $result")
        }
        println("Test passed: $result")
    }

    // Object test
    test(LiteralContainer(""), "null")
    test(LiteralContainer("null"), "null")
    test(LiteralContainer("false"), "false")
    test(LiteralContainer("5"), "5")
    test(LiteralContainer("17.9"), "17.9")
    test(LiteralContainer("'c'"), "'c'")
    test(LiteralContainer("\"something random $\""), "\"something random $\"")
    test(LiteralContainer("\$this is\$ 12 \$some comment\$"), "12")

    // Array test
    test(ArrayContainer("[]"), "[]")
    test(ArrayContainer("[1, 2, 3, [4, 5, 6, [7, 8, 9 ],], 10]"), "[1, 2, 3, [4, 5, 6, [7, 8, 9]], 10]")

    // Map test
    test(MapContainer("{}"), "{}")
    test(MapContainer("{data: 16, person: {name: \"Krimzo\", age: 22}}"), "{ data: 16, person: { name: \"Krimzo\", age: 22 } }")

    // Helper test
    val person = Person()
    person.fromContainer(MapContainer("{ name: \"Krimzo\", age: null }"))
    test(person, Person("Krimzo", null).toString())

    println("All tests passed!")
}
