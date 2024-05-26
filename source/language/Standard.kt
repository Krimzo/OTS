package language

typealias ArrayContainerType <T> = ArrayList<T>
typealias MapContainerType <K, V> = LinkedHashMap<K, V>

object Standard {
    const val nullValue: String = "null"
    const val falseValue: String = "false"
    const val trueValue: String = "true"

    const val char: Char = '\''
    const val string: Char = '"'
    const val comment: Char = '$'

    const val splitter: Char = ','
    const val assign: Char = ':'

    const val arrayStart: Char = '['
    const val arrayEnd: Char = ']'

    const val mapStart: Char = '{'
    const val mapEnd: Char = '}'
}
