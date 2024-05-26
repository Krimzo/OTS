package container

import language.Preprocessor

interface DataContainer {
    fun fromString(data: String, preprocessor: Preprocessor = Preprocessor()): Boolean
    override fun toString(): String
}

fun DataContainer.getBoolean(): Boolean? {
    if (this !is LiteralContainer) {
        return null
    }
    if (value is Boolean) {
        return value as Boolean
    }
    return null
}

fun DataContainer.getByte(): Byte? {
    if (this !is LiteralContainer) {
        return null
    }
    if (value is Number) {
        return (value as Number).toByte()
    }
    return null
}

fun DataContainer.getShort(): Short? {
    if (this !is LiteralContainer) {
        return null
    }
    if (value is Number) {
        return (value as Number).toShort()
    }
    return null
}

fun DataContainer.getInt(): Int? {
    if (this !is LiteralContainer) {
        return null
    }
    if (value is Number) {
        return (value as Number).toInt()
    }
    return null
}

fun DataContainer.getLong(): Long? {
    if (this !is LiteralContainer) {
        return null
    }
    if (value is Number) {
        return (value as Number).toLong()
    }
    return null
}

fun DataContainer.getFloat(): Float? {
    if (this !is LiteralContainer) {
        return null
    }
    if (value is Number) {
        return (value as Number).toFloat()
    }
    return null
}

fun DataContainer.getDouble(): Double? {
    if (this !is LiteralContainer) {
        return null
    }
    if (value is Number) {
        return (value as Number).toDouble()
    }
    return null
}

fun DataContainer.getChar(): Char? {
    if (this !is LiteralContainer) {
        return null
    }
    when (value) {
        is Number -> return (value as Number).toChar()
        is Char -> return value as Char
    }
    return null
}

fun DataContainer.getString(): String? {
    if (this !is LiteralContainer) {
        return null
    }
    return value?.toString()
}
