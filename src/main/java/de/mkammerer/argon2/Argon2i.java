package de.mkammerer.argon2;

import com.sun.jna.Native;

import de.mkammerer.argon2.jna.Argon2Library;
import de.mkammerer.argon2.jna.Size_t;
import de.mkammerer.argon2.jna.Uint32_t;

import java.nio.charset.Charset;
import java.security.SecureRandom;

/**
 * Argon2i password hashing function.
 */
class Argon2i implements Argon2 {
    /**
     * Salt length in bytes.
     */
    public static final int SALT_LENGTH = 16;
    /**
     * Hash length in bytes.
     */
    public static final int HASH_LENGTH = 32;

    /**
     * Secure RNG for salt.
     */
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * ASCII encoding.
     */
    private static final String ASCII_STR = "ASCII";
    private static final Charset ASCII = Charset.forName(ASCII_STR);

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

        int result = Argon2Library.INSTANCE.argon2i_hash_encoded(
                iterations_t, memory_t, parallelism_t, pwd, new Size_t(pwd.length),
                salt, new Size_t(salt.length), new Size_t(HASH_LENGTH), encoded, new Size_t(encoded.length)
        );
        if (result != Argon2Library.ARGON2_OK) {
            String errMsg = Argon2Library.INSTANCE.argon2_error_message(result);
            throw new IllegalStateException(String.format("%s (%d)", errMsg, result));
        }

        return Native.toString(encoded, ASCII_STR);
    }

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
    public boolean verify(String hash, String password) {
        //encoded needs to be nul terminated for strlen to work
        byte[] encoded = Native.toByteArray(hash, ASCII_STR);
        byte[] pwd = password.getBytes();

        int result = Argon2Library.INSTANCE.argon2i_verify(encoded, pwd, new Size_t(pwd.length));
        return result == Argon2Library.ARGON2_OK;
    }
}
