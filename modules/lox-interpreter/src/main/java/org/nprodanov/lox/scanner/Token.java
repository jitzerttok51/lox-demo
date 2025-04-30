package org.nprodanov.lox.scanner;

import org.nprodanov.lox.scanner.stream.CharacterStreamImpl;
import org.nprodanov.lox.scanner.stream.MarkableCharacterStream;

public record Token(TokenType type, String raw, int index, int position, int line) {

    public static Token from(TokenType type, String raw, CharacterStreamImpl stream) {
        return new Token(type, raw, stream.index(), stream.position(), stream.line());
    }

    public static Token from(TokenType type, MarkableCharacterStream stream) {
        return new Token(type, stream.slice(), stream.markIndex(), stream.markPosition(), stream.markLine());
    }
}
