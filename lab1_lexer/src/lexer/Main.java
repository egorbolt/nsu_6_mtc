package lexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
            Lexer lexer = new Lexer(br);
            lexer.curLexeme = lexer.getLexeme();
            double result = lexer.parseExpr();
            if (!lexer.curLexeme.getType().equals(LexemeType.EOF)) {
                System.err.println("Error: wrong math entry");
            }
            else {
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
