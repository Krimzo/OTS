
class Double3 : StorableObject {
    var x: Double
    var y: Double
    var z: Double

    constructor() : this(0.0)

    constructor(a: Double) : this(a, a, a)

    constructor(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }
}
