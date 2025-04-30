package org.nprodanov.lox.scanner.stream;

public interface MarkableCharacterStream extends CharacterStream {
    String slice();

    void mark();

    void gotoMark();

    int markIndex();

    int markPosition();

    int markLine();
}
