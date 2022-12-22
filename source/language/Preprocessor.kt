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

    private fun  removeWhitespace(data: String): String {
        var inChar = false
        var inString = false
        var inComment = false

        val builder = StringBuilder()
        for (value in data) {
            if (!inString && !inComment && value == Standard.char) {
                inChar = !inChar
            }
            if (!inChar && !inComment && value == Standard.string) {
                inString = !inString
            }
            if (!inChar && !inString && value == Standard.comment) {
                inComment = !inComment
            }

            if (!inComment) {
                if (inChar || inString) {
                    builder.append(value)
                }
                else if (!blacklist.contains(value)) {
                    builder.append(value)
                }
            }
        }
        return builder.toString()
    }

    companion object {
        private val blacklist: CharArray = charArrayOf(' ', '\t', '\n', Standard.comment)
    }
}
