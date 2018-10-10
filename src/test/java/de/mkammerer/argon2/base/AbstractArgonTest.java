package de.mkammerer.argon2.base;

import de.mkammerer.argon2.Argon2Advanced;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Random;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public abstract class AbstractArgonTest {
    protected static final Charset ASCII = Charset.forName("ASCII");
    protected static final Charset UTF8 = Charset.forName("utf-8");
    protected static final String PASSWORD = "password";
    protected static final String NOT_THE_PASSWORD = "not-the-password";
    protected static final int ITERATIONS = 2;
    protected static final int MEMORY = 1024;
    protected static final int PARALLELISM = 1;

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
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD);
        System.out.println(hash);

        assertThat(hash.startsWith(prefix), is(true));
        assertThat(sut.verify(hash, PASSWORD), is(true));
        assertThat(sut.verify(hash, NOT_THE_PASSWORD), is(false));
    }

    @Test
    public void testWithStringAndCharset() throws Exception {
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, ASCII);
        System.out.println(hash);

        assertThat(hash.startsWith(prefix), is(true));
        assertThat(sut.verify(hash, PASSWORD, ASCII), is(true));
        assertThat(sut.verify(hash, NOT_THE_PASSWORD, ASCII), is(false));
    }

    @Test
    public void testWithStringAndSaltString() throws Exception {
        String salt = new String(createSalt());
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, salt);
        System.out.println(hash);

        assertThat(hash.startsWith(prefix), is(true));
        assertThat(sut.verify(hash, PASSWORD), is(true));
        assertThat(sut.verify(hash, NOT_THE_PASSWORD), is(false));
    }

    @Test
    public void testWithChars() throws Exception {
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray());
        System.out.println(hash);

        assertThat(hash.startsWith(prefix), is(true));
        assertThat(sut.verify(hash, PASSWORD.toCharArray()), is(true));
        assertThat(sut.verify(hash, NOT_THE_PASSWORD.toCharArray()), is(false));
    }

    @Test
    public void testWithCharsAndCharset() throws Exception {
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), ASCII);
        System.out.println(hash);

        assertThat(hash.startsWith(prefix), is(true));
        assertThat(sut.verify(hash, PASSWORD.toCharArray(), ASCII), is(true));
        assertThat(sut.verify(hash, NOT_THE_PASSWORD.toCharArray(), ASCII), is(false));
    }

    @Test
    public void testUTF8() throws Exception {
        String password = "ŧҺìş ίŝ ứţƒ-8";
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, password, UTF8);
        assertThat(sut.verify(hash, password, UTF8), is(true));
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
        sut.hash(ITERATIONS, MEMORY, PARALLELISM, chars);

        assertThat(chars, equalTo(PASSWORD.toCharArray()));
    }

    @Test
    public void testRawWithString() throws Exception {
        byte[] salt = createSalt();
        byte[] hash = sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, salt);

        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, salt), is(hash));
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD, salt), is(not(hash)));
        byte[] notTheSalt = new byte[16];
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, notTheSalt), is(not(hash)));
    }

    @Test
    public void testRawWithStringAndCharset() throws Exception {
        byte[] salt = createSalt();
        byte[] hash = sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, ASCII, salt);

        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, ASCII, salt), is(hash));
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD, ASCII, salt), is(not(hash)));
        byte[] notTheSalt = new byte[16];
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, ASCII, notTheSalt), is(not(hash)));
    }

    @Test
    public void testRawWithChars() throws Exception {
        byte[] salt = createSalt();
        byte[] hash = sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), salt);

        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), salt), is(hash));
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD.toCharArray(), salt), is(not(hash)));
        byte[] notTheSalt = new byte[16];
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), notTheSalt), is(not(hash)));
    }

    @Test
    public void testRawWithCharsAndCharset() throws Exception {
        byte[] salt = createSalt();
        byte[] hash = sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), ASCII, salt);

        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), ASCII, salt), is(hash));
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD.toCharArray(), ASCII, salt), is(not(hash)));
        byte[] notTheSalt = new byte[16];
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), ASCII, notTheSalt), is(not(hash)));
    }

    protected byte[] getFixedSalt() {
        return "thisisthesalt".getBytes(UTF8);
    }

    protected Argon2Advanced getSut() {
        return sut;
    }

    private byte[] createSalt() {
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
