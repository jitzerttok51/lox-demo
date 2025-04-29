package org.nprodanov.lox.scanner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterStreamTest {

    @Test
    public void testEndOfFile() {
        String text = "abc";
        CharacterStream cs = new CharacterStreamV2(text);
        assertEquals(text.charAt(0), cs.get());
        assertEquals(text.charAt(1), cs.next().get());
        assertEquals(text.charAt(2), cs.next().get());
        assertTrue( cs.next().isEmpty());
        assertEquals(3, cs.position());
        assertTrue( cs.next().isEmpty());
        assertEquals(3, cs.position());
        assertTrue( cs.next().isEmpty());
        assertEquals(3, cs.position());
    }

    @Test
    public void testNewline() {
        String text = "abc\ntva";
        CharacterStream cs = new CharacterStreamV2(text);
        assertEquals(1, cs.line());
        cs.next();
        cs.next();
        cs.next();
        assertEquals(1, cs.line());
        cs.next();
        cs.next();
        assertEquals(2, cs.line());
    }

    @Test
    public void testPosition() {
        String text = "abc\ntva";
        CharacterStream cs = new CharacterStreamV2(text);
        assertEquals(1, cs.position());
        cs.next();
        assertEquals(2, cs.position());
        cs.next();
        assertEquals(3, cs.position());
        cs.next();
        assertEquals(4, cs.position());
        cs.next();
        assertEquals(1, cs.position());
        cs.next();
        assertEquals(2, cs.position());
    }

    @Test
    public void testRewind() {
        String text = "abc";
        CharacterStream cs = new CharacterStreamV2(text);
        assertEquals(text.charAt(0), cs.get());
        assertEquals(text.charAt(1), cs.next().get());
        assertEquals(text.charAt(2), cs.next().get());
        assertTrue( cs.next().isEmpty());
        cs.rewind();
        assertEquals(text.charAt(0), cs.get());
        assertEquals(text.charAt(1), cs.next().get());
        assertEquals(text.charAt(2), cs.next().get());
        assertTrue( cs.next().isEmpty());
    }

    @Test
    public void testWithMark() {
        String text = "test var foo";
        MarkableCharacterStream cs = new CharacterStreamV2(text);
        cs.next(); // e
        cs.next(); // s
        cs.next(); // t
        cs.next(); // ' '
        cs.next(); // v
        cs.mark();
        cs.next(); // a
        cs.next(); // r
        assertEquals("var", cs.slice());
        assertEquals(5, cs.markIndex());
        assertEquals(6, cs.markPosition());
        assertEquals(1, cs.markLine());
        cs.gotoMark();
        assertEquals('v', cs.get());
        assertEquals(5, cs.index());
        assertEquals(6, cs.position());
        assertEquals(1, cs.line());
    }
}