## About
JSON-like object serialisation for Kotlin objects.

## Example
```kotlin
import container.MapContainer

fun main() {
    val source = writeTest()
    println(source)
    readTest(source)
}

fun writeTest(): String {
    val container = MapContainer()
    container["doubles"] = Double3(1.0, 2.0, 3.0).toContainer()
    container["person"] = Person("Krimzo", 21.0).toContainer()
    return container.toString()
}

fun readTest(source: String) {
    val container = MapContainer(source)

    val doubles = Double3()
    doubles.fromContainer(container["doubles"] as MapContainer)
    println(doubles)

    val person = Person()
    person.fromContainer(container["person"] as MapContainer)
    println(person)
}
```
