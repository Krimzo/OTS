
class Person(val name: String, val age: Double) : StorableObject {
    constructor() : this("Unknown", 0.0)

    override fun toString(): String {
        return "(${name}: $age)"
    }
}
