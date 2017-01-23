package de.mkammerer.argon2;

import com.sun.jna.Native;
import de.mkammerer.argon2.jna.Argon2Library;
import de.mkammerer.argon2.jna.Uint32_t;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Argon2 base class.
 */
abstract class BaseArgon2 implements Argon2 {

    /**
     * ASCII encoding.
     */
    private static final String ASCII = "ASCII";

    /**
     * Default charset.
     */
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * Secure RNG for salt.
     */
    private final SecureRandom secureRandom = new SecureRandom();

    private final int saltLen;
    private final int hashLen;

    /**
     * Constructor.
     * <p>
     * Uses default salt and hash lengths.
     */
    public BaseArgon2() {
        this(Argon2Constants.DEFAULT_SALT_LENGTH, Argon2Constants.DEFAULT_HASH_LENGTH);
    }

    /**
     * Constructor.
     *
     * @param saltLen Salt length in bytes.
     * @param hashLen Hash length in bytes.
     */
    public BaseArgon2(int saltLen, int hashLen) {
        this.saltLen = saltLen;
        this.hashLen = hashLen;
    }

    /**
     * Returns the hash length in bytes.
     *
     * @return Hash length in bytes.
     */
    protected int getHashLength() {
        return hashLen;
    }

    /**
     * Returns the Argon2 type.
     *
     * @return Argon2 type.
     */
    protected abstract Argon2Factory.Argon2Types getType();

    /**
     * Generates {@link #saltLen} bytes of salt.
     *
     * @return Salt.
     */
    private byte[] generateSalt() {
        byte[] salt = new byte[saltLen];
        secureRandom.nextBytes(salt);

        return salt;
    }

    @Override
    public String hash(int iterations, int memory, int parallelism, char[] password) {
        return hash(iterations, memory, parallelism, password, DEFAULT_CHARSET);
    }

    @Override
    public String hash(int iterations, int memory, int parallelism, char[] password, Charset charset) {
        final byte[] pwd = toByteArray(password, charset);
        try {
            return hashBytes(iterations, memory, parallelism, pwd);
        } finally {
            wipeArray(pwd);
        }
    }

    @Override
    public String hash(int iterations, int memory, int parallelism, String password, Charset charset) {
        final byte[] pwd = password.getBytes(charset);
        try {
            return hashBytes(iterations, memory, parallelism, pwd);
        } finally {
            wipeArray(pwd);
        }
    }

    @Override
    public String hash(int iterations, int memory, int parallelism, String password) {
        return hash(iterations, memory, parallelism, password, DEFAULT_CHARSET);
    }

    private String hashBytes(int iterations, int memory, int parallelism, byte[] pwd) {
        final byte[] salt = generateSalt();

        final Uint32_t iterations_t = new Uint32_t(iterations);
        final Uint32_t memory_t = new Uint32_t(memory);
        final Uint32_t parallelism_t = new Uint32_t(parallelism);

        int len = Argon2Library.INSTANCE.argon2_encodedlen(iterations_t, memory_t, parallelism_t,
                new Uint32_t(salt.length), new Uint32_t(hashLen), getType().ordinal).intValue();
        final byte[] encoded = new byte[len];

        int result = callLibraryHash(pwd, salt, iterations_t, memory_t, parallelism_t, encoded);

        if (result != Argon2Library.ARGON2_OK) {
            String errMsg = Argon2Library.INSTANCE.argon2_error_message(result);
            throw new IllegalStateException(String.format("%s (%d)", errMsg, result));
        }

        return Native.toString(encoded, ASCII);
    }

    @Override
    public boolean verify(String hash, String password) {
        return verify(hash, password, DEFAULT_CHARSET);
    }

    @Override
    public boolean verify(String hash, String password, Charset charset) {
        byte[] pwd = password.getBytes(charset);
        try {
            return verifyBytes(hash, pwd);
        } finally {
            wipeArray(pwd);
        }
    }

    @Override
    public boolean verify(String hash, char[] password, Charset charset) {
        byte[] pwd = toByteArray(password, charset);
        try {
            return verifyBytes(hash, pwd);
        } finally {
            wipeArray(pwd);
        }
    }

    @Override
    public boolean verify(String hash, char[] password) {
        return verify(hash, password, DEFAULT_CHARSET);
    }

    private boolean verifyBytes(String hash, byte[] pwd) {
        // encoded needs to be nul terminated for strlen to work
        byte[] encoded = Native.toByteArray(hash, ASCII);
        int result = callLibraryVerify(encoded, pwd);

        return result == Argon2Library.ARGON2_OK;
    }

    /**
     * Is called when the hash function of the native library should be called.
     *
     * @param pwd         Password.
     * @param salt        Salt.
     * @param iterations  Iterations.
     * @param memory      Memory.
     * @param parallelism Parallelism.
     * @param encoded     Byte array to write the hash to.
     * @return Return code.
     */
    protected abstract int callLibraryHash(byte[] pwd, byte[] salt, Uint32_t iterations, Uint32_t memory, Uint32_t parallelism, byte[] encoded);

    /**
     * Is called when the verify function of the native library should be called.
     *
     * @param encoded Buffer.
     * @param pwd     Password.
     * @return Return code.
     */
    protected abstract int callLibraryVerify(byte[] encoded, byte[] pwd);

    /**
     * Wipes the data from the given array.
     * <p>
     * Use this method to clear confidential data after using it.
     *
     * @param array the array to wipe
     */
    private void wipeArray(byte[] array) {
        assert array != null;

        Arrays.fill(array, (byte) 0);
    }

    @Override
    public void wipeArray(char[] array) {
        Arrays.fill(array, (char) 0);
    }

    /**
     * Converts the given char array to a UTF-8 encoded byte array.
     *
     * @param chars   the char array to convert
     * @param charset Charset of the password
     * @return UTF-8 encoded byte array
     */
    private byte[] toByteArray(char[] chars, Charset charset) {
        assert chars != null;

        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = charset.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }
}
