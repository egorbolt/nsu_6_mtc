package lexer;

import java.io.IOException;
import java.io.Reader;

public class Lexer {
    Reader reader;
    char current;
    Lexeme curLexeme;
    int eof;

    public Lexer(Reader reader) {
        this.reader = reader;
        this.eof = -1;
    }

    public Lexeme getLexeme() {
        int readCharachter = 0;

        LexemeType type;
        StringBuilder value = new StringBuilder();

        if (eof == -1) {
            eof = 0;
            try {
                readCharachter = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (readCharachter == -1) {
                eof = 1;
                return new Lexeme(LexemeType.EOF, "");
            }
            else {
                current = (char) readCharachter;
            }
        }

        if (eof == 1) {
            return new Lexeme(LexemeType.EOF, "");
        }

        if (current >= '0' && current <= '9') {
            type = LexemeType.NUMBER;
            value.append(current);
            do {
                try {
                    readCharachter = reader.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (readCharachter != -1) {
                    current = (char) readCharachter;
                    if (current >= '0' && current <= '9') {
                        value.append(current);
                    }
                }
            } while (readCharachter != -1 && (current >= '0' && current <= '9'));
            return new Lexeme(type, value.toString());
        }
        else if (current == '+') {
            try {
                readCharachter = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (readCharachter != -1) {
                current = (char) readCharachter;
            }
            else {
                eof = 1;
            }
            return new Lexeme(LexemeType.PLUS, "");
        }
        else if (current == '-') {
            try {
                readCharachter = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (readCharachter != -1) {
                current = (char) readCharachter;
            }
            else {
                eof = 1;
            }
            return new Lexeme(LexemeType.MINUS, "");
        }
        else if (current == '*') {
            try {
                readCharachter = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (readCharachter != -1) {
                current = (char) readCharachter;
            }
            else {
                eof = 1;
            }
            return new Lexeme(LexemeType.MULT, "");
        }
        else if (current == '/') {
            try {
                readCharachter = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (readCharachter != -1) {
                current = (char) readCharachter;
            }
            else {
                eof = 1;
            }
            return new Lexeme(LexemeType.DIV, "");
        }
        else if (current == '^') {
            try {
                readCharachter = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (readCharachter != -1) {
                current = (char) readCharachter;
            }
            else {
                eof = 1;
            }
            return new Lexeme(LexemeType.POWER, "");
        }
        else if (current == '(') {
            try {
                readCharachter = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (readCharachter != -1) {
                current = (char) readCharachter;
            }
            else {
                eof = 1;
            }
            return new Lexeme(LexemeType.OPEN_BRACE, "");
        }
        else if (current == ')') {
            try {
                readCharachter = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (readCharachter != -1) {
                current = (char) readCharachter;
            }
            else {
                eof = 1;
            }
            return new Lexeme(LexemeType.CLOSE_BRACE, "");
        }

        return null;
    }

    public double parseExpr() {
        double tmp = parseTerm();
        while (curLexeme.getType().equals(LexemeType.PLUS) || curLexeme.getType().equals(LexemeType.MINUS)) {
            if (curLexeme.getType().equals(LexemeType.PLUS)) {
                curLexeme = getLexeme();
                tmp += parseTerm();
            }
            if (curLexeme.getType().equals(LexemeType.PLUS)) {
                curLexeme = getLexeme();
                tmp -= parseTerm();
            }
        }
        return tmp;
    }

    public double parseTerm() {
        double tmp = parseFactor();
        while (curLexeme.getType().equals(LexemeType.MULT) || curLexeme.getType().equals(LexemeType.DIV)) {
            if (curLexeme.getType().equals(LexemeType.MULT)) {
                curLexeme = getLexeme();
                tmp *= parseFactor();
            }
            if (curLexeme.getType().equals(LexemeType.DIV)) {
                curLexeme = getLexeme();
                double check = parseFactor();
                if (check == 0) {
                    System.err.println("Dividing by zero");
                    System.exit(-1);
                }
                else {
                    tmp /= check;
                }

            }
        }
        return tmp;
    }

    public double parseFactor() {
        double tmp = parsePower();
        if (curLexeme.getType().equals(LexemeType.POWER)) {
            curLexeme = getLexeme();
            tmp = Math.pow(tmp, parseFactor());
        }
        return tmp;
    }

    public double parsePower() {
        if (curLexeme.getType().equals(LexemeType.MINUS)) {
            curLexeme = getLexeme();
            return -parseAtom();
        }
        return parseAtom();
    }

    public double parseAtom() {
        if (curLexeme.getType().equals(LexemeType.NUMBER)) {
            String text = curLexeme.getValue();
            curLexeme = getLexeme();
            return Double.parseDouble(text);
        }
        if (curLexeme.getType().equals(LexemeType.OPEN_BRACE)) {
            curLexeme = getLexeme();
            double tmp = parseExpr();
            if (!curLexeme.getType().equals(LexemeType.CLOSE_BRACE)) {
                System.err.println("Error with closebrace");
                System.exit(-2);
            }
            return tmp;
        }
        return 0;
    }
}
