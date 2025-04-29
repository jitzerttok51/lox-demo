package org.nprodanov.lox.scanner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStreamTest {

    @Test
    public void testEndOfFile() {
        String text = "abc";
        CharacterStream cs = new CharacterStream(text);
        assertEquals(text.charAt(0), cs.get());
        assertEquals(text.charAt(1), cs.next().get());
        assertEquals(text.charAt(2), cs.next().get());
        assertTrue( cs.next().isEmpty());
    }

    @Test
    public void testBasicFunctionality() {
        String text = "Hi there all";
        CharacterStream cs = new CharacterStream(text);
        assertEquals(cs.get(), text.charAt(0));
        assertEquals(cs.next(), text.charAt(1));
        assertEquals(cs.next(), text.charAt(2));
        cs.push();
        assertEquals(cs.next(), text.charAt(3));
        assertEquals(cs.next(), text.charAt(4));
        assertEquals(cs.next(), text.charAt(5));
        assertEquals(cs.next(), text.charAt(6));
        cs.inc();
        assertEquals(cs.next(), text.charAt(8));
        cs.pop();
        assertEquals(2, cs.index());
        assertEquals(3, cs.position());
        assertEquals(1, cs.line());
    }

    @Test
    public void testSlicing() {
        String text = "Hi there all";
        CharacterStream cs = new CharacterStream(text);
        assertEquals(cs.get(), text.charAt(0));
        assertEquals(cs.next(), text.charAt(1));
        assertEquals(cs.next(), text.charAt(2));
        assertEquals(cs.next(), text.charAt(3));
        assertEquals(cs.next(), text.charAt(4));
        assertEquals(cs.next(), text.charAt(5));
        assertEquals(cs.next(), text.charAt(6));
        assertEquals(cs.slice(), text.substring(0, 6)); // "Hi the"re all

        cs.rewind();
        assertEquals(cs.get(), text.charAt(0));
        assertEquals(cs.next(), text.charAt(1));
        assertEquals(cs.next(), text.charAt(2));
        assertEquals(cs.next(), text.charAt(3));
        cs.push();
        assertEquals(cs.next(), text.charAt(4));
        assertEquals(cs.next(), text.charAt(5));
        assertEquals(cs.next(), text.charAt(6));
        assertEquals(cs.slice(), text.substring(3, 6)); // Hi "the"re all
        cs.pop();
        assertEquals(cs.slice(), text.substring(0, 3)); // "Hi "
    }
}