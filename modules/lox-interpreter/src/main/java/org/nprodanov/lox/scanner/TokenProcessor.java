package org.nprodanov.lox.scanner;

import org.nprodanov.lox.scanner.stream.MarkableCharacterStream;

import java.util.Optional;

@FunctionalInterface
public interface TokenProcessor {

    Optional<Token> process(MarkableCharacterStream stream);
}
