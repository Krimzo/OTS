import container.ArrayContainer
import container.MapContainer
import container.ObjectContainer
import utility.toFile
import utility.use

fun main() {
    // Read test
    println(ArrayContainer().use {
        it.fromString(" \n    \n[      \n      'C' , 17\n, {something=[0, 1, \"je xD puno $$\"], da=6, ne=12.7}]")
    })
    println(MapContainer().use {
        it.fromString("\n\t{da=2,ne='?'\$PECANJE\$, mozda =     17.9\n}\t")
    })
    println(Float3().use {
        it.fromContainer(MapContainer().use {
            it["x"] = ObjectContainer(4f)
            it["y"] = ObjectContainer(5f)
            it["z"] = ObjectContainer(6f)
        })
    })

    // Write test
    toFile("data.txt") {
        println(ObjectContainer())
        val primitive = ObjectContainer(false)
        println(primitive)

        println(MapContainer())
        val obj = MapContainer()
        obj["pecanje"] = ObjectContainer("HAHA")
        obj["mozda"] = ObjectContainer(6)
        println(obj)

        println(ArrayContainer())
        val array = ArrayContainer()
        array.add(ObjectContainer('C'))
        array.add(ObjectContainer(17))
        println(array)

        MapContainer().let { firstMap ->
            ArrayContainer().let { firstArray ->
                MapContainer().let {
                    it["da"] = ObjectContainer(6)
                    it["ne"] = ObjectContainer('C')
                    it["mozda"] = ObjectContainer("HAHA")
                    firstArray.add(it)
                }
                firstMap["firstArray"] = firstArray
            }
            println(firstMap)
        }

        println(Float3(1f, 2f, 3f).toContainer())
    }
}
