package de.mkammerer.argon2;

import java.security.SecureRandom;

/**
 * Argon2 base class.
 */
abstract class BaseArgon2 implements Argon2 {

    /**
     * Salt length in bytes.
     */
    public static final int SALT_LENGTH = 16;
    /**
     * Hash length in bytes.
     */
    public static final int HASH_LENGTH = 32;

    /**
     * ASCII encoding.
     */
    protected static final String ASCII = "ASCII";

    /**
     * Secure RNG for salt.
     */
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * Generates {@link #SALT_LENGTH} bytes of salt.
     *
     * @return Salt.
     */
    protected byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);

        return salt;
    }

}
