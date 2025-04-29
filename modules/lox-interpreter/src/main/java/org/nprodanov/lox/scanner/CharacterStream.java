package org.nprodanov.lox.scanner;

import java.util.Optional;

public interface CharacterStream {

    Optional<Character> next();

    char get();

    int index();

    int position();

    int line();

    void rewind();
}
