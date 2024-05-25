package container

import language.Preprocessor
import language.Standard
import utility.safe

class ObjectContainer : DataContainer {
    var value: Any? = null

    constructor() {
    }

    constructor(source: String) {
        fromString(source)
    }

    override fun fromString(data: String, preprocessor: Preprocessor): Boolean {
        val data = preprocessor.process(data)
        if (data.isEmpty()) {
            return false
        }

        // Null object
        if (data == Standard.nullValue) {
            value = null
            return true
        }

        // Booleans
        if (data == Standard.falseValue) {
            value = false
            return true
        }
        if (data == Standard.trueValue) {
            value = true
            return true
        }

        // Chars/Strings
        if (data.length >= 3 && data.first() == Standard.char && data.last() == Standard.char) {
            value = data[1] // currently doesn't support multi-character chars
            return true
        }
        if (data.length >= 2 && data.first() == Standard.string && data.last() == Standard.string) {
            value = data.substring(1, data.length - 1)
            return true
        }

        // Scalars
        if (safe { value = data.toLong() }) {
            return true
        }
        if (safe { value = data.toDouble() }) {
            return true
        }
        return false
    }

    override fun toString(): String {
        if (value is String) {
            return "\"$value\""
        }
        if (value is Char) {
            return "'$value'"
        }
        return value.toString()
    }
}
