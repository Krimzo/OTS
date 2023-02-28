package container

import language.Parser
import language.Preprocessor
import language.Standard

class ArrayContainer : ArrayList<DataContainer>(), DataContainer {
    override fun fromString(data: String, preprocessor: Preprocessor): Boolean {
        var data = preprocessor.process(data)

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
        return super.toString()
    }
}
