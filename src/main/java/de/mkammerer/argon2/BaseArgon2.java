package de.mkammerer.argon2;

import com.sun.jna.Native;
import de.mkammerer.argon2.jna.Argon2Library;
import de.mkammerer.argon2.jna.JnaUint32;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Argon2 base class.
 */
abstract class BaseArgon2 implements Argon2, Argon2Advanced {

    /**
     * ASCII encoding.
     */
    private static final String ASCII = "ASCII";

    /**
     * Default charset.
     */
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * The pattern that a hash must match.
     */
    private static final Pattern HASH_PATTERN = Pattern.compile("^\\$argon2[id]{1,2}\\$v=\\d+\\$m=(\\d+),t=(\\d+),p=(\\d+)\\$.+$");

    /**
     * Secure RNG for salt.
     */
    private final SecureRandom secureRandom = new SecureRandom();

    private final int defaultSaltLength;
    private final int defaultHashLength;

    /**
     * Constructor.
     *
     * @param defaultSaltLength Default salt length in bytes. Can be overridden by some methods.
     * @param defaultHashLength Default hash length in bytes. Can be overridden by some methods.
     */
    BaseArgon2(int defaultSaltLength, int defaultHashLength) {
        this.defaultSaltLength = defaultSaltLength;
        this.defaultHashLength = defaultHashLength;
    }

    @Override
    public String hash(int iterations, int memory, int parallelism, char[] password) {
        return hash(iterations, memory, parallelism, password, DEFAULT_CHARSET);
    }

    @Override
    public String hash(int iterations, int memory, int parallelism, byte[] data) {
        return hashBytes(iterations, memory, parallelism, data);
    }

    @Override
    public boolean verify(String hash, byte[] data) {
        return verifyBytes(hash, data);
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

    @Override
    public String hash(int iterations, int memory, int parallelism, char[] password, Charset charset, byte[] salt) {
        final byte[] pwd = toByteArray(password, charset);
        try {
            return hashBytes(iterations, memory, parallelism, pwd, salt);
        } finally {
            wipeArray(pwd);
        }
    }

    @Override
    public byte[] rawHash(int iterations, int memory, int parallelism, char[] password, byte[] salt) {
        return rawHash(iterations, memory, parallelism, password, DEFAULT_CHARSET, salt);
    }

    @Override
    public byte[] rawHash(int iterations, int memory, int parallelism, byte[] data, byte[] salt) {
        return rawHashBytes(iterations, memory, parallelism, data, salt, defaultHashLength);
    }

    @Override
    public byte[] rawHash(int iterations, int memory, int parallelism, char[] password, Charset charset, byte[] salt) {
        final byte[] pwd = toByteArray(password, charset);
        try {
            return rawHashBytes(iterations, memory, parallelism, pwd, salt, defaultHashLength);
        } finally {
            wipeArray(pwd);
        }
    }

    @Override
    public byte[] rawHash(int iterations, int memory, int parallelism, String password, Charset charset, byte[] salt) {
        final byte[] pwd = password.getBytes(charset);
        try {
            return rawHashBytes(iterations, memory, parallelism, pwd, salt, defaultHashLength);
        } finally {
            wipeArray(pwd);
        }
    }

    @Override
    public byte[] rawHash(int iterations, int memory, int parallelism, String password, byte[] salt) {
        return rawHash(iterations, memory, parallelism, password, DEFAULT_CHARSET, salt);
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

    /**
     * Wipes the data from the given array.
     * <p>
     * Use this method to clear confidential data after using it.
     *
     * @param array the array to wipe
     */
    @Override
    public void wipeArray(byte[] array) {
        Arrays.fill(array, (byte) 0);
    }

    @Override
    public void wipeArray(char[] array) {
        Arrays.fill(array, (char) 0);
    }

    @Override
    public byte[] pbkdf(int iterations, int memory, int parallelism, char[] password, Charset charset, byte[] salt, int keyLength) {
        final byte[] pwd = toByteArray(password, charset);
        try {
            return pbkdf(iterations, memory, parallelism, pwd, salt, keyLength);
        } finally {
            wipeArray(pwd);
        }
    }

    @Override
    public byte[] pbkdf(int iterations, int memory, int parallelism, byte[] password, byte[] salt, int keyLength) {
        return rawHashBytes(iterations, memory, parallelism, password, salt, keyLength);
    }

    @Override
    public boolean needsRehash(String hash, int iterations, int memory, int parallelism) {
        Matcher matcher = HASH_PATTERN.matcher(hash);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid hash '" + hash + "'");
        }

        int actualMemory = Integer.parseInt(matcher.group(1));
        int actualIterations = Integer.parseInt(matcher.group(2));
        int actualParallelism = Integer.parseInt(matcher.group(3));
        return actualMemory < memory || actualIterations < iterations || actualParallelism < parallelism;
    }

    /**
     * Returns the hash length in bytes.
     *
     * @return Hash length in bytes.
     */
    protected int getDefaultHashLength() {
        return defaultHashLength;
    }

    /**
     * Returns the Argon2 type.
     *
     * @return Argon2 type.
     */
    protected abstract Argon2Factory.Argon2Types getType();

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
    protected abstract int callLibraryHash(byte[] pwd, byte[] salt, JnaUint32 iterations, JnaUint32 memory, JnaUint32 parallelism, byte[] encoded);

    /**
     * Is called when the hash function of the native library should be called.
     *
     * @param pwd         Password.
     * @param salt        Salt.
     * @param iterations  Iterations.
     * @param memory      Memory.
     * @param parallelism Parallelism.
     * @param hash        Byte array to write the hash to.
     * @return Return code.
     */
    protected abstract int callLibraryRawHash(byte[] pwd, byte[] salt, JnaUint32 iterations, JnaUint32 memory, JnaUint32 parallelism, byte[] hash);

    /**
     * Is called when the verify function of the native library should be called.
     *
     * @param encoded Buffer.
     * @param pwd     Password.
     * @return Return code.
     */
    protected abstract int callLibraryVerify(byte[] encoded, byte[] pwd);

    /**
     * Generates {@link #defaultSaltLength} bytes of salt.
     *
     * @return Salt.
     */
    private byte[] generateSalt() {
        byte[] salt = new byte[defaultSaltLength];
        secureRandom.nextBytes(salt);

        return salt;
    }

    private String hashBytes(int iterations, int memory, int parallelism, byte[] pwd) {
        final byte[] salt = generateSalt();
        return hashBytes(iterations, memory, parallelism, pwd, salt);
    }

    private String hashBytes(int iterations, int memory, int parallelism, byte[] pwd, byte[] salt) {
        final JnaUint32 jnaIterations = new JnaUint32(iterations);
        final JnaUint32 jnaMemory = new JnaUint32(memory);
        final JnaUint32 jnaParallelism = new JnaUint32(parallelism);

        int len = Argon2Library.INSTANCE.argon2_encodedlen(jnaIterations, jnaMemory, jnaParallelism,
                new JnaUint32(salt.length), new JnaUint32(defaultHashLength), getType().getJnaType()).intValue();
        final byte[] encoded = new byte[len];

        int result = callLibraryHash(pwd, salt, jnaIterations, jnaMemory, jnaParallelism, encoded);
        checkResult(result);

        return Native.toString(encoded, ASCII);
    }

    private void checkResult(int result) {
        if (result != Argon2Library.ARGON2_OK) {
            String errMsg = Argon2Library.INSTANCE.argon2_error_message(result);
            throw new IllegalStateException(String.format("%s (%d)", errMsg, result));
        }
    }

    private byte[] rawHashBytes(int iterations, int memory, int parallelism, byte[] pwd, byte[] salt, int hashLength) {
        final JnaUint32 jnaIterations = new JnaUint32(iterations);
        final JnaUint32 jnaMemory = new JnaUint32(memory);
        final JnaUint32 jnaParallelism = new JnaUint32(parallelism);

        final byte[] hash = new byte[hashLength];

        int result = callLibraryRawHash(pwd, salt, jnaIterations, jnaMemory, jnaParallelism, hash);
        checkResult(result);

        return hash;
    }

    private boolean verifyBytes(String hash, byte[] pwd) {
        // encoded needs to be nul terminated for strlen to work
        byte[] encoded = Native.toByteArray(hash, ASCII);
        int result = callLibraryVerify(encoded, pwd);

        return result == Argon2Library.ARGON2_OK;
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
