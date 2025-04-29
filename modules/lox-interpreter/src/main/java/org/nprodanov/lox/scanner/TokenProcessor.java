package org.nprodanov.lox.scanner;

import java.util.Optional;

@FunctionalInterface
public interface TokenProcessor {

    Optional<Token> process(CharacterStream stream);
}
