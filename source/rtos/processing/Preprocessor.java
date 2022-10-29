package rtos.processing;

import rtos.standard.Syntax;

public final class Preprocessor {
    private Preprocessor() {}

    public static String processRawData(String data) throws SyntaxException {
        data = removeWhitespace(data);
        data = evaluateMacros(data);
        return data;
    }

    private static String removeWhitespace(String data) {
        data = data.replaceAll("\n", "");
        data = data.replaceAll("\r", "");
        data = data.replaceAll("\t", "    ");
        data = removeUnusedSpaces(data);
        return data;
    }

    private static String removeUnusedSpaces(String data) {
        StringBuilder builder = new StringBuilder();
        boolean insideComment = false;
        boolean insideLiteral = false;
        for (char c : data.toCharArray()) {
            if (c == Syntax.charWrapper || c == Syntax.stringWrapper) {
                insideLiteral = !insideLiteral;
            }

            if (c == Syntax.commentWrapper) {
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

    private static String evaluateMacros(String data) throws SyntaxException {
        for (int index = data.indexOf(Syntax.macroSplit); index >= 0; index = data.indexOf(Syntax.macroSplit)) {
            final int startIndex = data.lastIndexOf(Syntax.macroStart, index);
            if (startIndex < 0) {
                throw new SyntaxException("Macro start syntax error");
            }
            final String key = data.substring(startIndex, index);

            final int endIndex = data.indexOf(Syntax.macroEnd, index);
            if (endIndex < 0) {
                throw new SyntaxException("Macro end syntax error");
            }
            final String value = data.substring(index + 1, endIndex);

            data = data.replaceAll(data.substring(startIndex, endIndex + 1), "");
            data = data.replaceAll(key, value);
        }

        if (data.contains(String.valueOf(Syntax.macroSplit))) {
            throw new SyntaxException("Unused macro found");
        }

        if (data.contains(String.valueOf(Syntax.macroStart))) {
            throw new SyntaxException("Unknown macro found");
        }

        return data;
    }
}
