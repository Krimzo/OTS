package container

import language.MapContainerType
import language.Parser
import language.Preprocessor
import language.Standard

class MapContainer : MapContainerType<String, DataContainer>, DataContainer {
    constructor() {
    }

    constructor(source: String) {
        fromString(source)
    }

    override fun fromString(data: String, preprocessor: Preprocessor): Boolean {
        var data = preprocessor.process(data)
        if (data.isEmpty()) {
            return false
        }

        // Length/(first, last) check
        if (data.length < 2) {
            return false
        }
        if (data.first() != Standard.mapStart || data.last() != Standard.mapEnd) {
            return false
        }

        // Remove (first, last)
        data = data.substring(1, data.length - 1)
        if (data.isNotEmpty() && data.last() != Standard.splitter) {
            data += Standard.splitter
        }

        // Split to parts and parse each part
        this.clear()
        for (part in Parser.splitMapData(data)) {
            for (container in arrayOf(LiteralContainer(), ArrayContainer(), MapContainer())) {
                if (container.fromString(part.value)) {
                    this[part.key] = container
                    break
                }
            }
        }
        return true
    }

    override fun toString(): String {
        if (this.isEmpty()) {
            return "${Standard.mapStart}${Standard.mapEnd}"
        }

        val builder = StringBuilder()
        builder.append(Standard.mapStart).append(' ')
        val iterator = this.iterator()
        while (iterator.hasNext()) {
            val (key, value) = iterator.next()
            builder.append(key)
            builder.append(Standard.assign).append(' ')
            builder.append(value.toString())
            if (iterator.hasNext()) {
                builder.append(Standard.splitter).append(' ')
            }
        }
        builder.append(' ').append(Standard.mapEnd)
        return builder.toString()
    }
}
