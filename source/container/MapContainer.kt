package container

import language.Parser
import language.Preprocessor
import language.Standard

class MapContainer : LinkedHashMap<String, DataContainer>(), DataContainer {
    override fun fromString(data: String, preprocessor: Preprocessor): Boolean {
        var data = preprocessor.process(data)

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
            for (container in arrayOf(ObjectContainer(), ArrayContainer(), MapContainer())) {
                if (container.fromString(part.value)) {
                    this[part.key] = container
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
