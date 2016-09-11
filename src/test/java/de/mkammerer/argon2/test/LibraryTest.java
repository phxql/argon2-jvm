package de.mkammerer.argon2.test;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LibraryTest {
    @Test
    public void testArgon2i() throws Exception {
        Argon2 sut = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);

        String hash = sut.hash(2, 65536, 1, "password");
        System.out.println(hash);

        assertThat(hash.startsWith("$argon2i$"), is(true));
        assertThat(sut.verify(hash, "password"), is(true));
        assertThat(sut.verify(hash, "not-the-password"), is(false));
    }

    @Test
    public void testArgon2iWithChars() throws Exception {
        Argon2 sut = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);

        String hash = sut.hash(2, 65536, 1, "password".toCharArray());
        System.out.println(hash);

        assertThat(hash.startsWith("$argon2i$"), is(true));
        assertThat(sut.verify(hash, "password".toCharArray()), is(true));
        assertThat(sut.verify(hash, "not-the-password".toCharArray()), is(false));
    }

    @Test
    public void testArgon2d() throws Exception {
        Argon2 sut = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);

        String hash = sut.hash(2, 65536, 1, "password");
        System.out.println(hash);

        assertThat(hash.startsWith("$argon2d$"), is(true));
        assertThat(sut.verify(hash, "password"), is(true));
        assertThat(sut.verify(hash, "not-the-password"), is(false));
    }

    @Test
    public void testArgon2dWithChars() throws Exception {
        Argon2 sut = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);

        String hash = sut.hash(2, 65536, 1, "password".toCharArray());
        System.out.println(hash);

        assertThat(hash.startsWith("$argon2d$"), is(true));
        assertThat(sut.verify(hash, "password".toCharArray()), is(true));
        assertThat(sut.verify(hash, "not-the-password".toCharArray()), is(false));
    }

    @Test
    public void testArgon2iWithArgs() throws Exception {
        Argon2 sut = Argon2Factory.create(32, 64);

        String hash = sut.hash(2, 65536, 1, "password");
        System.out.println(hash);

        assertThat(hash.startsWith("$argon2i$"), is(true));
        assertThat(sut.verify(hash, "password"), is(true));
        assertThat(sut.verify(hash, "not-the-password"), is(false));
    }

    @Test
    public void testArgon2dWithArgs() throws Exception {
        Argon2 sut = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d, 32, 64);

        String hash = sut.hash(2, 65536, 1, "password");
        System.out.println(hash);

        assertThat(hash.startsWith("$argon2d$"), is(true));
        assertThat(sut.verify(hash, "password"), is(true));
        assertThat(sut.verify(hash, "not-the-password"), is(false));
    }

    @Test
    public void testUTF8() throws Exception {
        String password = "ŧҺìş ίŝ ứţƒ-8";

        Argon2 sut = Argon2Factory.create();
        String hash = sut.hash(2, 65535, 1, password, Charset.forName("UTF-8"));
        assertThat(sut.verify(hash, password, Charset.forName("UTF-8")), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidParameters() throws Exception {
        Argon2 sut = Argon2Factory.create();

        sut.hash(0, 0, 0, "password");
    }

    @Test
    public void testWipeArray() throws Exception {
        char[] array = "Hello, Argon2".toCharArray();

        Argon2 sut = Argon2Factory.create();
        sut.wipeArray(array);

        for (char c : array) {
            assertThat(c, is((char) 0));
        }
    }
}