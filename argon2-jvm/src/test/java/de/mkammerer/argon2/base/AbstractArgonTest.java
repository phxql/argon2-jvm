package de.mkammerer.argon2.base;

import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Constants;
import de.mkammerer.argon2.Argon2Version;
import de.mkammerer.argon2.HashResult;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

@SuppressWarnings("deprecation")
public abstract class AbstractArgonTest {
    protected static final Charset ASCII = Charset.forName("ASCII");
    protected static final Charset UTF8 = Charset.forName("utf-8");
    protected static final String PASSWORD = "password";
    protected static final String NOT_THE_PASSWORD = "not-the-password";
    protected static final String SECRET = "secret";
    protected static final String NOT_THE_SECRET = "not-the-secret";
    protected static final String ASSOCIATED_DATA = "associated-data";
    protected static final String NOT_THE_ASSOCIATED_DATA = "not-the-associated-data";
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


    @Test
    public void testHashBytes() throws UnsupportedEncodingException {
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.getBytes(UTF8));
        System.out.println(hash);

        assertThat(hash.startsWith(prefix), is(true));
        assertThat(sut.verify(hash, PASSWORD.getBytes(UTF8)), is(true));
        assertThat(sut.verify(hash, NOT_THE_PASSWORD.getBytes(UTF8)), is(false));
    }


    @Test(expected = IllegalStateException.class)
    public void testHashWithInvalidParameters() throws Exception {
        sut.hash(0, 0, 0, PASSWORD);
    }

    @Test(expected = IllegalStateException.class)
    public void testRawHashWithInvalidParameters() throws Exception {
        byte[] salt = createSalt();
        sut.rawHash(0, 0, 0, PASSWORD, salt);
    }

    @Test
    public void testWipeArrayChars() throws Exception {
        char[] array = "Hello, Argon2".toCharArray();

        sut.wipeArray(array);

        for (char c : array) {
            assertThat(c, is((char) 0));
        }
    }

    @Test
    public void testWipeArrayBytes() throws Exception {
        byte[] array = "Hello, Argon2".getBytes(UTF8);

        sut.wipeArray(array);

        for (byte b : array) {
            assertThat(b, is((byte) 0));
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
    public void testRawWithBytes() throws Exception {
        byte[] salt = createSalt();
        byte[] hash = sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.getBytes(ASCII), salt);

        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.getBytes(ASCII), salt), is(hash));
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD.getBytes(ASCII), salt), is(not(hash)));
        byte[] notTheSalt = new byte[16];
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.getBytes(ASCII), notTheSalt), is(not(hash)));
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

    @Test
    public void testPbkdfWithChars() {
        byte[] salt = createSalt();
        int keyLength = 512 / 8;

        byte[] key1 = sut.pbkdf(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), ASCII, salt, keyLength);
        byte[] key2 = sut.pbkdf(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), ASCII, salt, keyLength);
        byte[] key3 = sut.pbkdf(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD.toCharArray(), ASCII, salt, keyLength);

        assertThat(key1.length, is(keyLength));
        assertThat(key2.length, is(keyLength));
        assertThat(key3.length, is(keyLength));

        assertThat(key1, is(key2));
        assertThat(key1, is(not(key3)));
    }

    @Test
    public void testPbkdfWithBytes() throws Exception {
        byte[] salt = createSalt();
        int keyLength = 512 / 8;

        byte[] key1 = sut.pbkdf(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.getBytes(ASCII), salt, keyLength);
        byte[] key2 = sut.pbkdf(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.getBytes(ASCII), salt, keyLength);
        byte[] key3 = sut.pbkdf(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD.getBytes(ASCII), salt, keyLength);

        assertThat(key1.length, is(keyLength));
        assertThat(key2.length, is(keyLength));
        assertThat(key3.length, is(keyLength));

        assertThat(key1, is(key2));
        assertThat(key1, is(not(key3)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNeedsRehashWithInvalidHash() {
        sut.needsRehash("asiudgui3478fo sm", ITERATIONS, MEMORY, PARALLELISM);
    }

    @Test
    public void testHashAdvanced() throws Exception {
        byte[] password = PASSWORD.getBytes(UTF8);
        byte[] salt = createSalt();
        int keyLength = 512 / 8;

        for (Argon2Version version : Argon2Version.values()) {
            HashResult result = sut.hashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, salt, keyLength, version);

            assertThat(result.getRaw().length, is(keyLength));
            assertThat(result.getEncoded(), startsWith(prefix));
            assertThat(sut.verify(result.getEncoded(), password), is(true));
        }
    }

    @Test
    public void testRawHashAdvancedWithStringAndCharset() throws Exception {
        char[] password = PASSWORD.toCharArray();
        char[] notPassword = NOT_THE_PASSWORD.toCharArray();
        byte[] salt = createSalt();
        byte[] notSalt = createSalt();
        byte[] secret = SECRET.getBytes(ASCII);
        byte[] notSecret = NOT_THE_SECRET.getBytes(ASCII);
        byte[] ad = ASSOCIATED_DATA.getBytes(ASCII);
        byte[] notAd = NOT_THE_ASSOCIATED_DATA.getBytes(ASCII);

        byte[] hash = sut.rawHashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, secret, ad);

        assertThat(sut.rawHashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, secret, ad), is(hash));
        assertThat(sut.rawHashAdvanced(ITERATIONS, MEMORY, PARALLELISM, notPassword, ASCII, salt, secret, ad), is(not(hash)));
        assertThat(sut.rawHashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, notSalt, secret, ad), is(not(hash)));
        assertThat(sut.rawHashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, notSecret, ad), is(not(hash)));
        assertThat(sut.rawHashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, secret, notAd), is(not(hash)));

        assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, secret, ad, hash), is(true));
        assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, notPassword, ASCII, salt, secret, ad, hash), is(false));
        assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, notSalt, secret, ad, hash), is(false));
        assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, notSecret, ad, hash), is(false));
        assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, secret, notAd, hash), is(false));
    }

    @Test
    public void testRawHashAdvancedWithBytes() throws Exception {
        byte[] password = PASSWORD.getBytes(ASCII);
        byte[] notPassword = NOT_THE_PASSWORD.getBytes(ASCII);
        byte[] salt = createSalt();
        byte[] notSalt = createSalt();
        byte[] secret = SECRET.getBytes(ASCII);
        byte[] notSecret = NOT_THE_SECRET.getBytes(ASCII);
        byte[] ad = ASSOCIATED_DATA.getBytes(ASCII);
        byte[] notAd = NOT_THE_ASSOCIATED_DATA.getBytes(ASCII);
        int keyLength = 512 / 8;

        for (Argon2Version version : Argon2Version.values()) {
            byte[] hash = sut.rawHashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, salt, secret, ad, keyLength, version);

            assertThat(hash.length, is(keyLength));
            assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, salt, secret, ad, keyLength, version, hash), is(true));
            assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, notPassword, salt, secret, ad, keyLength, version, hash), is(false));
            assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, notSalt, secret, ad, keyLength, version, hash), is(false));
            assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, salt, notSecret, ad, keyLength, version, hash), is(false));
            assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, salt, secret, notAd, keyLength, version, hash), is(false));
        }
    }

    @Test
    public void generateSalt() throws Exception {
        byte[] salt1 = sut.generateSalt();
        byte[] salt2 = sut.generateSalt();

        assertThat(salt1, is(notNullValue()));
        assertThat(salt2, is(notNullValue()));

        assertThat(salt1.length, is(Argon2Constants.DEFAULT_SALT_LENGTH));
        assertThat(salt2.length, is(Argon2Constants.DEFAULT_SALT_LENGTH));

        assertThat(salt1, is(not(salt2)));
    }

    @Test
    public void generateSaltWithLength() throws Exception {
        byte[] salt1 = sut.generateSalt(32);
        byte[] salt2 = sut.generateSalt(32);

        assertThat(salt1, is(notNullValue()));
        assertThat(salt2, is(notNullValue()));

        assertThat(salt1.length, is(32));
        assertThat(salt2.length, is(32));

        assertThat(salt1, is(not(salt2)));
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
