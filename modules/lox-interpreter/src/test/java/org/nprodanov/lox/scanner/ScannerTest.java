package org.nprodanov.lox.scanner;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    @Test
    public void test() {
        String raw = "{}().,";
        Scanner scanner = new Scanner(raw);
        List<Token> tokens = scanner.read();

        assertEquals(new Token(TokenType.LEFT_BRACKET, "{", 0, 1, 1), tokens.getFirst());
        assertEquals(new Token(TokenType.RIGHT_BRACKET, "}", 1, 2, 1), tokens.get(1));
        assertEquals(new Token(TokenType.LEFT_BRACE, "(", 2, 3, 1), tokens.get(2));
        assertEquals(new Token(TokenType.RIGHT_BRACE, ")", 3, 4, 1), tokens.get(3));
        assertEquals(new Token(TokenType.COMMA, ",", 5, 6, 1), tokens.get(5));
        assertEquals(new Token(TokenType.DOT, ".", 4, 5, 1), tokens.get(4));
    }

    @Test
    public void testArithmetics() {
        String raw = "+-*/=";
        Scanner scanner = new Scanner(raw);
        List<Token> tokens = scanner.read();

        assertEquals(new Token(TokenType.PLUS, "+", 0, 1, 1), tokens.getFirst());
        assertEquals(new Token(TokenType.MINUS, "-", 1, 2, 1), tokens.get(1));
        assertEquals(new Token(TokenType.ASTERIX, "*", 2, 3, 1), tokens.get(2));
        assertEquals(new Token(TokenType.SLASH, "/", 3, 4, 1), tokens.get(3));
        assertEquals(new Token(TokenType.EQUALS, "=", 4, 5, 1), tokens.get(4));
//        assertEquals(new Token(TokenType.DOT, ".", 4, 5, 1), tokens.get(4));
    }

    @Test
    public void testDoubleOperators() {
        String raw = "!= == *= ";
        Scanner scanner = new Scanner(raw);
        List<Token> tokens = scanner.read();

        assertEquals(new Token(TokenType.BANG_EQUALS, "!=", 0, 1, 1), tokens.getFirst());
        assertEquals(new Token(TokenType.EQUALS_EQUALS, "==", 3, 4, 1), tokens.get(1));
        assertEquals(new Token(TokenType.ASTERIX, "*", 6, 7, 1), tokens.get(2));
        assertEquals(new Token(TokenType.EQUALS, "=", 7, 8, 1), tokens.get(3));
    }
}