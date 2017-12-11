package de.mkammerer.argon2;

/**
 * Commonly used constants.
 */
public final class Argon2Constants {
    /**
     * Salt length in bytes.
     */
    public static final int DEFAULT_SALT_LENGTH = 16;

    /**
     * Hash length in bytes.
     */
    public static final int DEFAULT_HASH_LENGTH = 32;

    /**
     * Static class, no instances allowed.
     */
    private Argon2Constants() {
    }
}
