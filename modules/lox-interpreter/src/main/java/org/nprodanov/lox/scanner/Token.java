package org.nprodanov.lox.scanner;

public record Token(TokenType type, String raw, int index, int position, int line) {

    public static Token from(TokenType type, String raw, CharacterStream stream) {
        return new Token(type, raw, stream.index(), stream.position(), stream.line());
    }

    public static Token from(TokenType type, CharacterStream stream) {
        return new Token(type, stream.slice(), stream.index(), stream.position(), stream.line());
    }
}
