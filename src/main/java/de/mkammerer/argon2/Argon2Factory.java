package de.mkammerer.argon2;

import de.mkammerer.argon2.jna.Argon2_type;

/**
 * Factory for {@link Argon2} instances.
 */
public final class Argon2Factory {
    /**
     * Static class, no instances allowed.
     */
    private Argon2Factory() {
    }

    /**
     * Creates a new {@link Argon2} instance. This will select the Argon2i algorithm.
     *
     * @return Argon2 instance.
     */
    public static Argon2 create() {
        return createInternal(Argon2Types.ARGON2i, Argon2Constants.DEFAULT_SALT_LENGTH, Argon2Constants.DEFAULT_HASH_LENGTH);
    }

    /**
     * Creates a new {@link Argon2Advanced} instance. This will select the Argon2i algorithm.
     *
     * @return Argon2Advanced instance.
     */
    public static Argon2Advanced createAdvanced() {
        return createInternal(Argon2Types.ARGON2i, Argon2Constants.DEFAULT_SALT_LENGTH, Argon2Constants.DEFAULT_HASH_LENGTH);
    }

    /**
     * Creates a new {@link Argon2} instance. This will select the Argon2i algorithm.
     *
     * @param defaultSaltLength Default salt length in bytes. Can be overridden by some methods.
     * @param defaultHashLength Default hash length in bytes. Can be overridden by some methods.
     * @return Argon2 instance.
     */
    public static Argon2 create(int defaultSaltLength, int defaultHashLength) {
        return createInternal(Argon2Types.ARGON2i, defaultSaltLength, defaultHashLength);
    }

    /**
     * Creates a new {@link Argon2Advanced} instance. This will select the Argon2i algorithm.
     *
     * @param defaultSaltLength Default salt length in bytes. Can be overridden by some methods.
     * @param defaultHashLength Default hash length in bytes. Can be overridden by some methods.
     * @return Argon2Advanced instance.
     */
    public static Argon2Advanced createAdvanced(int defaultSaltLength, int defaultHashLength) {
        return createInternal(Argon2Types.ARGON2i, defaultSaltLength, defaultHashLength);
    }

    /**
     * Creates a new {@link Argon2} instance with the given type.
     *
     * @param type Argon2 type.
     * @return Argon2 instance.
     */
    public static Argon2 create(Argon2Types type) {
        return createInternal(type, Argon2Constants.DEFAULT_SALT_LENGTH, Argon2Constants.DEFAULT_HASH_LENGTH);
    }

    /**
     * Creates a new {@link Argon2Advanced} instance with the given type.
     *
     * @param type Argon2 type.
     * @return Argon2Advanced instance.
     */
    public static Argon2Advanced createAdvanced(Argon2Types type) {
        return createInternal(type, Argon2Constants.DEFAULT_SALT_LENGTH, Argon2Constants.DEFAULT_HASH_LENGTH);
    }

    /**
     * Creates a new {@link Argon2Advanced} instance with the given type.
     *
     * @param type              Argon2 type.
     * @param defaultSaltLength Default salt length in bytes. Can be overridden by some methods.
     * @param defaultHashLength Default hash length in bytes. Can be overridden by some methods.
     * @return Argon2Advanced instance.
     */
    public static Argon2Advanced createAdvanced(Argon2Types type, int defaultSaltLength, int defaultHashLength) {
        return createInternal(type, defaultSaltLength, defaultHashLength);
    }

    /**
     * Creates a new {@link Argon2} instance with the given type.
     *
     * @param type              Argon2 type.
     * @param defaultSaltLength Default salt length in bytes. Can be overridden by some methods.
     * @param defaultHashLength Default hash length in bytes. Can be overridden by some methods.
     * @return Argon2 instance.
     */
    public static Argon2 create(Argon2Types type, int defaultSaltLength, int defaultHashLength) {
        return createInternal(type, defaultSaltLength, defaultHashLength);
    }

    private static Argon2Advanced createInternal(Argon2Types type, int defaultSaltLength, int defaultHashLength) {
        switch (type) {
            case ARGON2i:
                return new Argon2i(defaultSaltLength, defaultHashLength);
            case ARGON2d:
                return new Argon2d(defaultSaltLength, defaultHashLength);
            case ARGON2id:
                return new Argon2id(defaultSaltLength, defaultHashLength);
            default:
                throw new IllegalArgumentException("Invalid argon2 type");
        }
    }

    /**
     * Argon2 type.
     */
    public enum Argon2Types {
        /**
         * Argon2i.
         */
        ARGON2i(1),
        /**
         * Argon2d.
         */
        ARGON2d(0),
        /**
         * Argon2id
         */
        ARGON2id(2);

        private final Argon2_type jnaType;

        /**
         * Constructor.
         *
         * @param idx Index from the Argon2 API.
         */
        Argon2Types(int idx) {
            this.jnaType = new Argon2_type(idx);
        }

        public Argon2_type getJnaType() {
            return jnaType;
        }
    }
}
