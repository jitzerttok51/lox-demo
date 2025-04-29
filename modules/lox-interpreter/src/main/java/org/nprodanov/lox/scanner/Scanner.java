package org.nprodanov.lox.scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Scanner {

    private final CharacterStream stream;

    public Scanner(String raw) {
        this.stream = new CharacterStream(raw);
    }

    public List<Token> read() {
        List<Token> tokens = new ArrayList<>();
        do {
            zip(
                    wrap(Scanner::readDoubleCharacterTypes),
                    wrap(Scanner::readSingleCharacterTypes),
                    wrap(Scanner::throwException)
            ).process(stream).ifPresent(tokens::add);
        } while (stream.next().isPresent());

        return List.copyOf(tokens);
    }

    private static Optional<Token> readSingleCharacterTypes(CharacterStream stream) {
        return Optional.ofNullable(
                switch (stream.get()) {
                    case '{' -> Token.from(TokenType.LEFT_BRACKET, stream);
                    case '}' -> Token.from(TokenType.RIGHT_BRACKET, stream);
                    case '(' -> Token.from(TokenType.LEFT_BRACE, stream);
                    case ')' -> Token.from(TokenType.RIGHT_BRACE, stream);
                    case '.' -> Token.from(TokenType.DOT, stream);
                    case ',' -> Token.from(TokenType.COMMA, stream);
                    case '+' -> Token.from(TokenType.PLUS, stream);
                    case '-' -> Token.from(TokenType.MINUS, stream);
                    case '/' -> Token.from(TokenType.SLASH, stream);
                    case '*' -> Token.from(TokenType.ASTERIX, stream);
                    case '=' -> Token.from(TokenType.EQUALS, stream);
                    default -> null;
                }
        );
    }

    private static Optional<Token> throwException(CharacterStream stream) {
        if(List.of('\n', '\t', ' ').contains(stream.get())) {
            return Optional.empty();
        }
        throw new RuntimeException("Invalid character '"+stream.get()+"'");
    }

    private static Optional<Token> readDoubleCharacterTypes(CharacterStream stream) {
        char curr = stream.get();
        if(stream.next().map(c-> c != '=').orElse(true)) {
            return Optional.empty();
        }
        return Optional.ofNullable(
                switch (curr) {
                    case '>' -> Token.from(TokenType.GREATER_EQUALS, stream);
                    case '<' -> Token.from(TokenType.LESS_EQUALS, stream);
                    case '=' -> Token.from(TokenType.EQUALS_EQUALS, stream);
                    case '!' -> Token.from(TokenType.BANG_EQUALS, stream);
                    default -> null;
                }
        );
    }

    @SafeVarargs
    private <T> Optional<T> zip(Optional<T> ...optionals) {
        for (Optional<T> option : optionals) {
            if(option.isPresent()) {
                return option;
            }
        }

        return Optional.empty();
    }

    private TokenProcessor zip(TokenProcessor... processors) {
        return cs-> {
            for (TokenProcessor processor : processors) {
                Optional<Token> result = processor.process(cs);
                if(result.isPresent()) {
                    return result;
                }
            }
            return Optional.empty();
        };
    }


    private TokenProcessor wrap(TokenProcessor processor) {
        return cs -> {
          cs.push();
          Optional<Token> token = processor.process(cs);
          if(token.isEmpty()) {
              cs.pop();
          }
          return token;
        };
    }
}
