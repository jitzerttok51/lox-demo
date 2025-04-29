package org.nprodanov.lox.scanner;

import java.util.ArrayDeque;
import java.util.Optional;

public class CharacterStream {

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
    private final ArrayDeque<Position> stack = new ArrayDeque<>();

    public CharacterStream(String raw) {
        this.raw = raw;
        rewind();
    }

    char get() {
        return raw.charAt(position.index());
    }

    Optional<Character> next() {
        if(isEnd()) {
            return Optional.empty();
        }
        inc();
        return Optional.of(get());
    }

    public String slice() {
        int start = 0;
        int end = position.index()+1;
        if(!stack.isEmpty()) {
            start = stack.peek().index();
        }

        return raw.substring(start, end);
    }

    public void inc() {
        if(isEnd()) {
            return;
        }
        position = position.advance(get() == '\n');
    }

    public void push() {
        stack.push(position);
    }

    public void pop() {
        position = stack.pop();
    }

    public int index() {
        return position.index();
    }

    public int position() {
        return position.position();
    }

    public int line() {
        return position.line();
    }

    public void rewind() {
        position = new Position(0, 1, 1);
    }

    public boolean isEnd() {
        return index() == raw.length() - 1;
    }

    @Override
    public String toString() {
        return "'%c':%d".formatted(get(), position.index());
    }
}
