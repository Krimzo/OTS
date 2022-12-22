package container

import language.Parser
import language.Preprocessor
import language.Standard

class ArrayContainer : ArrayList<DataContainer>(), DataContainer {
    override fun fromString(data: String, preprocessor: Preprocessor): Boolean {
        if (data.isEmpty()) {
            return false
        }

        var data = preprocessor.process(data)

        if (data.first() != Standard.arrayStart || data.last() != Standard.arrayEnd) {
            return false
        }

        data = data.substring(1, data.length - 1)
        if (data.last() != Standard.splitter) {
            data += Standard.splitter
        }

        this.clear()
        for (part in Parser.parseArray(data)) {
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
