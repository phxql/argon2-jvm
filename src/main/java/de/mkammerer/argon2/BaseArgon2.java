package de.mkammerer.argon2;

import com.sun.jna.Native;
import de.mkammerer.argon2.jna.Argon2Library;
import de.mkammerer.argon2.jna.Uint32_t;

import java.security.SecureRandom;

/**
 * Argon2 base class.
 */
abstract class BaseArgon2 implements Argon2 {

    /**
     * Salt length in bytes.
     */
    private static final int SALT_LENGTH = 16;
    /**
     * Hash length in bytes.
     */
    protected static final int HASH_LENGTH = 32;

    /**
     * ASCII encoding.
     */
    private static final String ASCII = "ASCII";

    /**
     * Secure RNG for salt.
     */
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * Generates {@link #SALT_LENGTH} bytes of salt.
     *
     * @return Salt.
     */
    private byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);

        return salt;
    }

    @Override
    public String hash(int iterations, int memory, int parallelism, String password) {
        final byte[] pwd = password.getBytes();
        final byte[] salt = generateSalt();

        final Uint32_t iterations_t = new Uint32_t(iterations);
        final Uint32_t memory_t = new Uint32_t(memory);
        final Uint32_t parallelism_t = new Uint32_t(parallelism);

        int len = Argon2Library.INSTANCE.argon2_encodedlen(iterations_t, memory_t, parallelism_t,
                new Uint32_t(salt.length), new Uint32_t(HASH_LENGTH)).intValue();
        final byte[] encoded = new byte[len];

        int result = callLibraryHash(pwd, salt, iterations_t, memory_t, parallelism_t, encoded);
        if (result != Argon2Library.ARGON2_OK) {
            String errMsg = Argon2Library.INSTANCE.argon2_error_message(result);
            throw new IllegalStateException(String.format("%s (%d)", errMsg, result));
        }

        return Native.toString(encoded, ASCII);
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

    @Override
    public boolean verify(String hash, String password) {
        //encoded needs to be nul terminated for strlen to work
        byte[] encoded = Native.toByteArray(hash, ASCII);
        byte[] pwd = password.getBytes();

        int result = callLibraryVerify(encoded, pwd);
        return result == Argon2Library.ARGON2_OK;
    }

    /**
     * Is called when the verify function of the native library should be called.
     *
     * @param encoded Buffer.
     * @param pwd     Password.
     * @return Return code.
     */
    protected abstract int callLibraryVerify(byte[] encoded, byte[] pwd);
}
