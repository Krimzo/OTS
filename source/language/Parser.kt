package language

object Parser {
    fun splitArrayData(data: String): ArrayList<String> {
        var scopeLevel = 0
        var inChar = false
        var inString = false

        var builder = StringBuilder()
        val result = ArrayList<String>()
        for (value in data) {
            when (value) {
                Standard.char -> {
                    inChar = !inChar
                    scopeLevel += if (inChar) 1 else -1
                }
                Standard.string -> {
                    inString = !inString
                    scopeLevel += if (inString) 1 else -1
                }
                Standard.arrayStart -> {
                    scopeLevel += 1
                }
                Standard.arrayEnd -> {
                    scopeLevel -= 1
                }
                Standard.mapStart -> {
                    scopeLevel += 1
                }
                Standard.mapEnd -> {
                    scopeLevel -= 1
                }
            }

            if (scopeLevel == 0 && value == Standard.splitter) {
                result.add(builder.toString())
                builder = StringBuilder()
            }
            else {
                builder.append(value)
            }
        }
        return result
    }

    fun splitMapData(data: String): LinkedHashMap<String, String> {
        val result = LinkedHashMap<String, String>()
        for (part in splitArrayData(data)) {
            val pieces = part.split(Standard.assign, limit=2)
            if (pieces.size == 2) {
                result[pieces[0]] = pieces[1]
            }
        }
        return result
    }
}