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
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
