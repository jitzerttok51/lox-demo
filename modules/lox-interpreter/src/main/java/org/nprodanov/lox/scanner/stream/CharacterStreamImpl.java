package org.nprodanov.lox.scanner.stream;

import java.util.Optional;

public class CharacterStreamImpl implements MarkableCharacterStream {

    private record Position(int index, int line, int position) {

        private Position advance(boolean newLine) {
            if(newLine) {
                return new Position(index+1, line+1, 1);
            }
            return new Position(index+1, line, position+1);
        }
    }

    private final String raw;
    private Position position;

    private Position mark;

    public CharacterStreamImpl(String raw) {
        this.raw = raw;
        rewind();
    }

    @Override
    public char get() {
        return raw.charAt(position.index());
    }

    @Override
    public Optional<Character> next() {
        if(isEnd()) {
            return Optional.empty();
        }
        position = position.advance(get() == '\n');
        return Optional.of(get());
    }

//    public String slice() {
//        int start = 0;
//        int end = position.index()+1;
//        if(!stack.isEmpty()) {
//            start = stack.peek().index();
//        }
//
//        return raw.substring(start, end);
//    }

    @Override
    public int index() {
        return position.index();
    }

    @Override
    public int position() {
        return position.position();
    }

    @Override
    public int line() {
        return position.line();
    }

    @Override
    public void rewind() {
        position = new Position(0, 1, 1);
        mark = new Position(0, 1, 1);
    }

    @Override
    public void mark() {
        mark = position;
    }

    @Override
    public void gotoMark() {
        position = mark;
    }

    @Override
    public int markIndex() {
        return mark.index();
    }

    @Override
    public int markPosition() {
        return mark.position();
    }

    @Override
    public int markLine() {
        return mark.line();
    }

    @Override
    public String slice() {
        return raw.substring(markIndex(), index()+1);
    }

    private boolean isEnd() {
        return index() == raw.length() - 1;
    }

    @Override
    public String toString() {
        return "'%c':%d".formatted(get(), position.index());
    }
}
