package container

import language.ArrayContainerType
import language.Parser
import language.Preprocessor
import language.Standard

class ArrayContainer : ArrayContainerType<DataContainer>, DataContainer {
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
        if (data.first() != Standard.arrayStart || data.last() != Standard.arrayEnd) {
            return false
        }

        // Remove (first, last)
        data = data.substring(1, data.length - 1)
        if (data.isNotEmpty() && data.last() != Standard.splitter) {
            data += Standard.splitter
        }

        // Split to parts and parse each part
        this.clear()
        for (part in Parser.splitArrayData(data)) {
            for (container in arrayOf(ObjectContainer(), ArrayContainer(), MapContainer())) {
                if (container.fromString(part)) {
                    this.add(container)
                    break
                }
            }
        }
        return true
    }

    override fun toString(): String {
        if (this.isEmpty()) {
            return "${Standard.arrayStart}${Standard.arrayEnd}"
        }

        val builder = StringBuilder()
        builder.append(Standard.arrayStart)
        val iterator = this.iterator()
        while (iterator.hasNext()) {
            builder.append(iterator.next().toString())
            if (iterator.hasNext()) {
                builder.append(Standard.splitter).append(' ')
            }
        }
        builder.append(Standard.arrayEnd)
        return builder.toString()
    }
}
