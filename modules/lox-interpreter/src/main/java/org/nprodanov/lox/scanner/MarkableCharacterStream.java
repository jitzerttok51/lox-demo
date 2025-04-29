package org.nprodanov.lox.scanner;

public interface MarkableCharacterStream extends CharacterStream {
    String slice();

    void mark();

    void gotoMark();

    int markIndex();

    int markPosition();

    int markLine();
}
