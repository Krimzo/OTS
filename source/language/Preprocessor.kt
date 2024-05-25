package language

class Preprocessor : HashMap<String, String>() {
    fun process(data: String): String {
        var result = data
        result = applyMacros(result)
        result = removeWhitespace(result)
        return result
    }

    private fun applyMacros(data: String): String {
        var result = data
        for (macro in this) {
            result = result.replace(macro.key, macro.value)
        }
        return result
    }

    private fun removeWhitespace(data: String): String {
        var inChar = false
        var inString = false
        var inComment = false
        val builder = StringBuilder()
        for (value in data) {
            if (value == Standard.comment && !inChar && !inString) {
                inComment = !inComment
                continue
            }
            if (inComment)
                continue

            if (value == Standard.char && !inString) {
                inChar = !inChar
            }
            if (value == Standard.string && !inChar) {
                inString = !inString
            }

            if (inChar || inString || !value.isWhitespace()) {
                builder.append(value)
            }
        }
        return builder.toString()
    }
}
