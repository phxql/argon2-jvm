package de.mkammerer.argon2.base;

import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Constants;
import de.mkammerer.argon2.Argon2Version;
import de.mkammerer.argon2.HashResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("deprecation")
public abstract class AbstractArgonTest {
    protected static final Charset ASCII = StandardCharsets.US_ASCII;
    protected static final Charset UTF8 = StandardCharsets.UTF_8;
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

    @BeforeEach
    public void setUp() throws Exception {
        sut = createSut();
        prefix = getHashPrefix();
    }

    @Test
    public void testWithString() throws Exception {
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD);
        System.out.println(hash);

        assertThat(hash.startsWith(prefix)).isTrue();
        assertThat(sut.verify(hash, PASSWORD)).isTrue();
        assertThat(sut.verify(hash, NOT_THE_PASSWORD)).isFalse();
    }

    @Test
    public void testWithStringAndCharset() throws Exception {
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, ASCII);
        System.out.println(hash);

        assertThat(hash.startsWith(prefix)).isTrue();
        assertThat(sut.verify(hash, PASSWORD, ASCII)).isTrue();
        assertThat(sut.verify(hash, NOT_THE_PASSWORD, ASCII)).isFalse();
    }

    @Test
    public void testWithChars() throws Exception {
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray());
        System.out.println(hash);

        assertThat(hash.startsWith(prefix)).isTrue();
        assertThat(sut.verify(hash, PASSWORD.toCharArray())).isTrue();
        assertThat(sut.verify(hash, NOT_THE_PASSWORD.toCharArray())).isFalse();
    }

    @Test
    public void testWithCharsAndCharset() throws Exception {
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), ASCII);
        System.out.println(hash);

        assertThat(hash.startsWith(prefix)).isTrue();
        assertThat(sut.verify(hash, PASSWORD.toCharArray(), ASCII)).isTrue();
        assertThat(sut.verify(hash, NOT_THE_PASSWORD.toCharArray(), ASCII)).isFalse();
    }

    @Test
    public void testUTF8() throws Exception {
        String password = "ŧҺìş ίŝ ứţƒ-8";
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, password, UTF8);
        assertThat(sut.verify(hash, password, UTF8)).isTrue();
    }


    @Test
    public void testHashBytes() throws UnsupportedEncodingException {
        String hash = sut.hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.getBytes(UTF8));
        System.out.println(hash);

        assertThat(hash.startsWith(prefix)).isTrue();
        assertThat(sut.verify(hash, PASSWORD.getBytes(UTF8))).isTrue();
        assertThat(sut.verify(hash, NOT_THE_PASSWORD.getBytes(UTF8))).isFalse();
    }


    @Test
    public void testHashWithInvalidParameters() throws Exception {
        assertThatThrownBy(() ->
                sut.hash(0, 0, 0, PASSWORD)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testRawHashWithInvalidParameters() throws Exception {
        byte[] salt = createSalt();
        assertThatThrownBy(() ->
                sut.rawHash(0, 0, 0, PASSWORD, salt)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testWipeArrayChars() throws Exception {
        char[] array = "Hello, Argon2".toCharArray();

        sut.wipeArray(array);

        for (char c : array) {
            assertThat(c).isEqualTo((char) 0);
        }
    }

    @Test
    public void testWipeArrayBytes() throws Exception {
        byte[] array = "Hello, Argon2".getBytes(UTF8);

        sut.wipeArray(array);

        for (byte b : array) {
            assertThat(b).isEqualTo((byte) 0);
        }
    }

    @Test
    public void testCharsAreLeftIntact() throws Exception {
        char[] chars = PASSWORD.toCharArray();
        sut.hash(ITERATIONS, MEMORY, PARALLELISM, chars);

        assertThat(chars).isEqualTo(PASSWORD.toCharArray());
    }

    @Test
    public void testRawWithString() throws Exception {
        byte[] salt = createSalt();
        byte[] hash = sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, salt);

        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, salt)).isEqualTo(hash);
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD, salt)).isNotEqualTo(hash);
        byte[] notTheSalt = new byte[16];
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, notTheSalt)).isNotEqualTo(hash);
    }

    @Test
    public void testRawWithStringAndCharset() throws Exception {
        byte[] salt = createSalt();
        byte[] hash = sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, ASCII, salt);

        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, ASCII, salt)).isEqualTo(hash);
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD, ASCII, salt)).isNotEqualTo(hash);
        byte[] notTheSalt = new byte[16];
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD, ASCII, notTheSalt)).isNotEqualTo(hash);
    }

    @Test
    public void testRawWithChars() throws Exception {
        byte[] salt = createSalt();
        byte[] hash = sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), salt);

        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), salt)).isEqualTo(hash);
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD.toCharArray(), salt)).isNotEqualTo(hash);
        byte[] notTheSalt = new byte[16];
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), notTheSalt)).isNotEqualTo(hash);
    }

    @Test
    public void testRawWithBytes() throws Exception {
        byte[] salt = createSalt();
        byte[] hash = sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.getBytes(ASCII), salt);

        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.getBytes(ASCII), salt)).isEqualTo(hash);
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD.getBytes(ASCII), salt)).isNotEqualTo(hash);
        byte[] notTheSalt = new byte[16];
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.getBytes(ASCII), notTheSalt)).isNotEqualTo(hash);
    }

    @Test
    public void testRawWithCharsAndCharset() throws Exception {
        byte[] salt = createSalt();
        byte[] hash = sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), ASCII, salt);

        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), ASCII, salt)).isEqualTo(hash);
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD.toCharArray(), ASCII, salt)).isNotEqualTo(hash);
        byte[] notTheSalt = new byte[16];
        assertThat(sut.rawHash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), ASCII, notTheSalt)).isNotEqualTo(hash);
    }

    @Test
    public void testPbkdfWithChars() {
        byte[] salt = createSalt();
        int keyLength = 512 / 8;

        byte[] key1 = sut.pbkdf(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), ASCII, salt, keyLength);
        byte[] key2 = sut.pbkdf(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), ASCII, salt, keyLength);
        byte[] key3 = sut.pbkdf(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD.toCharArray(), ASCII, salt, keyLength);

        assertThat(key1.length).isEqualTo(keyLength);
        assertThat(key2.length).isEqualTo(keyLength);
        assertThat(key3.length).isEqualTo(keyLength);

        assertThat(key1).isEqualTo(key2);
        assertThat(key1).isNotEqualTo(key3);
    }

    @Test
    public void testPbkdfWithBytes() throws Exception {
        byte[] salt = createSalt();
        int keyLength = 512 / 8;

        byte[] key1 = sut.pbkdf(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.getBytes(ASCII), salt, keyLength);
        byte[] key2 = sut.pbkdf(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.getBytes(ASCII), salt, keyLength);
        byte[] key3 = sut.pbkdf(ITERATIONS, MEMORY, PARALLELISM, NOT_THE_PASSWORD.getBytes(ASCII), salt, keyLength);

        assertThat(key1.length).isEqualTo(keyLength);
        assertThat(key2.length).isEqualTo(keyLength);
        assertThat(key3.length).isEqualTo(keyLength);

        assertThat(key1).isEqualTo(key2);
        assertThat(key1).isNotEqualTo(key3);
    }

    @Test
    public void testNeedsRehashWithInvalidHash() {
        assertThatThrownBy(() ->
                sut.needsRehash("asiudgui3478fo sm", ITERATIONS, MEMORY, PARALLELISM)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testHashAdvanced() throws Exception {
        byte[] password = PASSWORD.getBytes(UTF8);
        byte[] salt = createSalt();
        int keyLength = 512 / 8;

        for (Argon2Version version : Argon2Version.values()) {
            HashResult result = sut.hashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, salt, keyLength, version);

            assertThat(result.getRaw().length).isEqualTo(keyLength);
            assertThat(result.getEncoded()).startsWith(prefix);
            assertThat(sut.verify(result.getEncoded(), password)).isTrue();
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

        assertThat(sut.rawHashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, secret, ad)).isEqualTo(hash);
        assertThat(sut.rawHashAdvanced(ITERATIONS, MEMORY, PARALLELISM, notPassword, ASCII, salt, secret, ad)).isNotEqualTo(hash);
        assertThat(sut.rawHashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, notSalt, secret, ad)).isNotEqualTo(hash);
        assertThat(sut.rawHashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, notSecret, ad)).isNotEqualTo(hash);
        assertThat(sut.rawHashAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, secret, notAd)).isNotEqualTo(hash);

        assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, secret, ad, hash)).isTrue();
        assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, notPassword, ASCII, salt, secret, ad, hash)).isFalse();
        assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, notSalt, secret, ad, hash)).isFalse();
        assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, notSecret, ad, hash)).isFalse();
        assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, ASCII, salt, secret, notAd, hash)).isFalse();
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

            assertThat(hash.length).isEqualTo(keyLength);
            assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, salt, secret, ad, keyLength, version, hash)).isTrue();
            assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, notPassword, salt, secret, ad, keyLength, version, hash)).isFalse();
            assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, notSalt, secret, ad, keyLength, version, hash)).isFalse();
            assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, salt, notSecret, ad, keyLength, version, hash)).isFalse();
            assertThat(sut.verifyAdvanced(ITERATIONS, MEMORY, PARALLELISM, password, salt, secret, notAd, keyLength, version, hash)).isFalse();
        }
    }

    @Test
    public void generateSalt() throws Exception {
        byte[] salt1 = sut.generateSalt();
        byte[] salt2 = sut.generateSalt();

        assertThat(salt1).isNotNull();
        assertThat(salt2).isNotNull();

        assertThat(salt1.length).isEqualTo(Argon2Constants.DEFAULT_SALT_LENGTH);
        assertThat(salt2.length).isEqualTo(Argon2Constants.DEFAULT_SALT_LENGTH);

        assertThat(salt1).isNotEqualTo(salt2);
    }

    @Test
    public void generateSaltWithLength() throws Exception {
        byte[] salt1 = sut.generateSalt(32);
        byte[] salt2 = sut.generateSalt(32);

        assertThat(salt1).isNotNull();
        assertThat(salt2).isNotNull();

        assertThat(salt1.length).isEqualTo(32);
        assertThat(salt2.length).isEqualTo(32);

        assertThat(salt1).isNotEqualTo(salt2);
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
