package de.mkammerer.argon2;

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
    private static final Charset ASCII = Charset.forName("ASCII");

    /**
     * UTF-8 encoding.
     */
    private static final Charset UTF8 = Charset.forName("UTF8");

    @Override
    public String hash(int iterations, int memory, int parallelism, String password) {
        byte[] pwd = password.getBytes(UTF8);
        byte[] salt = generateSalt();

        byte[] encoded = new byte[HASH_LENGTH * 4];

        int result = Argon2Library.INSTANCE.argon2i_hash_encoded(
                new Uint32_t(iterations), new Uint32_t(memory), new Uint32_t(parallelism), pwd, new Size_t(pwd.length),
                salt, new Size_t(salt.length), new Size_t(HASH_LENGTH), encoded, new Size_t(encoded.length)
        );
        if (result != Argon2Library.ARGON2_OK) {
            throw new IllegalStateException("Expected return code " + Argon2Library.ARGON2_OK + ", got " + result + ". See https://github.com/P-H-C/phc-winner-argon2/blob/master/src/argon2.h for return codes.");
        }

        return new String(encoded, ASCII);
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
        byte[] encoded = hash.getBytes(ASCII);
        byte[] pwd = password.getBytes(UTF8);

        int result = Argon2Library.INSTANCE.argon2i_verify(encoded, pwd, new Size_t(pwd.length));

        return result == Argon2Library.ARGON2_OK;
    }
}
