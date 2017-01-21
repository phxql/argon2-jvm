package de.mkammerer.argon2;

import de.mkammerer.argon2.jna.Argon2_type;

/**
 * Commonly used constants.
 */
public final class Argon2Constants {
    /**
     * Static class, no instances allowed.
     */
    private Argon2Constants() {
    }

    /**
     * Salt length in bytes.
     */
    public static final int DEFAULT_SALT_LENGTH = 16;

    /**
     * Hash length in bytes.
     */
    public static final int DEFAULT_HASH_LENGTH = 32;

    /**
     * Argon2 type.
     */
    public enum Argon2Types {
        /**
         * Argon2i.
         */
        ARGON2i,
        /**
         * Argon2d.
         */
        ARGON2d,
        /**
         * Argon2id
         */
        ARGON2id;

        public final Argon2_type ordinal;
        Argon2Types() {
            this.ordinal = new Argon2_type(this.ordinal());
        }
    }
}
