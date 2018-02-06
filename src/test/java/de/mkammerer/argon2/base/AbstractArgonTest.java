package de.mkammerer.argon2.base;

import de.mkammerer.argon2.Argon2Advanced;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Random;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public abstract class AbstractArgonTest {
    private static final String PASSWORD = "password";
    private static final String NOT_THE_PASSWORD = "not-the-password";

    private final Random random = new Random();

    private Argon2Advanced sut;
    private String prefix;


    protected abstract Argon2Advanced createSut();

    protected abstract String getHashPrefix();

    @Before
    public void setUp() throws Exception {
        sut = createSut();
        prefix = getHashPrefix();
    }

    @Test
    public void testWithString() throws Exception {
        String hash = sut.hash(2, 65536, 1, PASSWORD);
        System.out.println(hash);

        assertThat(hash.startsWith(prefix), is(true));
        assertThat(sut.verify(hash, PASSWORD), is(true));
        assertThat(sut.verify(hash, NOT_THE_PASSWORD), is(false));
    }

    @Test
    public void testWithStringAndCharset() throws Exception {
        Charset ascii = Charset.forName("ASCII");
        String hash = sut.hash(2, 65536, 1, PASSWORD, ascii);
        System.out.println(hash);

        assertThat(hash.startsWith(prefix), is(true));
        assertThat(sut.verify(hash, PASSWORD, ascii), is(true));
        assertThat(sut.verify(hash, NOT_THE_PASSWORD, ascii), is(false));
    }

    @Test
    public void testWithChars() throws Exception {
        String hash = sut.hash(2, 65536, 1, PASSWORD.toCharArray());
        System.out.println(hash);

        assertThat(hash.startsWith(prefix), is(true));
        assertThat(sut.verify(hash, PASSWORD.toCharArray()), is(true));
        assertThat(sut.verify(hash, NOT_THE_PASSWORD.toCharArray()), is(false));
    }

    @Test
    public void testWithCharsAndCharset() throws Exception {
        Charset ascii = Charset.forName("ASCII");
        String hash = sut.hash(2, 65536, 1, PASSWORD.toCharArray(), ascii);
        System.out.println(hash);

        assertThat(hash.startsWith(prefix), is(true));
        assertThat(sut.verify(hash, PASSWORD.toCharArray(), ascii), is(true));
        assertThat(sut.verify(hash, NOT_THE_PASSWORD.toCharArray(), ascii), is(false));
    }

    @Test
    public void testUTF8() throws Exception {
        String password = "ŧҺìş ίŝ ứţƒ-8";
        Charset utf8 = Charset.forName("UTF-8");

        String hash = sut.hash(2, 65535, 1, password, utf8);
        assertThat(sut.verify(hash, password, utf8), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidParameters() throws Exception {
        sut.hash(0, 0, 0, PASSWORD);
    }

    @Test
    public void testWipeArray() throws Exception {
        char[] array = "Hello, Argon2".toCharArray();

        sut.wipeArray(array);

        for (char c : array) {
            assertThat(c, is((char) 0));
        }
    }

    @Test
    public void testCharsAreLeftIntact() throws Exception {
        char[] chars = PASSWORD.toCharArray();
        sut.hash(2, 65536, 1, chars);

        assertThat(chars, equalTo(PASSWORD.toCharArray()));
    }

    @Test
    public void testRawHash() throws Exception {
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        byte[] hash = sut.rawHash(2, 65536, 1, PASSWORD, salt);

        assertThat(sut.rawHash(2, 65536, 1, PASSWORD, salt), is(hash));
        assertThat(sut.rawHash(2, 65536, 1, NOT_THE_PASSWORD, salt), is(not(hash)));
    }

}
