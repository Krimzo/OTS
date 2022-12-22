package container

import language.Preprocessor

interface DataContainer {
    fun fromString(data: String, preprocessor: Preprocessor = Preprocessor()): Boolean
    override fun toString(): String
}
