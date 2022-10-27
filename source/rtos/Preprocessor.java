package rtos;

public final class Preprocessor {
    public Preprocessor() {}

    public String process(String data) throws Exception {
        data = removeWhitespace(data);
        data = runPreprocessor(data);
        return data;
    }

    private String removeUnusedSpaces(String data) {
        StringBuilder builder = new StringBuilder();
        boolean insideComment = false;
        boolean insideLiteral = false;
        for (char c : data.toCharArray()) {
            if (c == '"' || c == '\'') {
                insideLiteral = !insideLiteral;
            }

            if (c == '$') {
                insideComment = !insideComment;
            }
            else if (!insideComment) {
                if (insideLiteral) {
                    builder.append(c);
                }
                else if (c != ' ') {
                    builder.append(c);
                }
            }
        }
        return builder.toString();
    }

    private String removeWhitespace(String data) {
        data = data.replaceAll("\n", "");
        data = data.replaceAll("\r", "");
        data = data.replaceAll("\t", "    ");
        data = removeUnusedSpaces(data);
        return data;
    }

    private String runPreprocessor(String data) throws Exception {
        for (int index = data.indexOf("->"); index >= 0; index = data.indexOf("->")) {
            final int keyIndex = data.lastIndexOf('#', index);
            if (keyIndex < 0) {
                throw new Exception("Macro declarations require '#' as first char");
            }
            final String key = data.substring(keyIndex, index);

            final int valueIndex = data.indexOf(';', index);
            if (valueIndex < 0) {
                throw new Exception("Macro values require ';' as last char");
            }
            final String value = data.substring(index + 2, valueIndex);

            data = data.replaceAll(data.substring(keyIndex, valueIndex + 1), "");
            data = data.replaceAll(key, value);
        }

        if (data.indexOf('#') >= 0) {
            throw new Exception("Unknown macro used");
        }

        return data;
    }
}
