import container.ArrayContainer
import container.MapContainer
import container.ObjectContainer
import container.fromContainer

class Person {
    var name: String = ""
    var age: Double? = 0.0

    constructor() {
    }

    constructor(name: String, age: Double?) {
        this.name = name
        this.age = age
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
    test(ObjectContainer(""), "null")
    test(ObjectContainer("null"), "null")
    test(ObjectContainer("false"), "false")
    test(ObjectContainer("5"), "5")
    test(ObjectContainer("17.9"), "17.9")
    test(ObjectContainer("'c'"), "'c'")
    test(ObjectContainer("\"something random $\""), "\"something random $\"")
    test(ObjectContainer("\$this is\$ 12 \$some comment\$"), "12")

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
