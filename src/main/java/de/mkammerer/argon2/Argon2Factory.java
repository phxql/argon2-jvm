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
     * Creates a new {@link Argon2} instance.
     *
     * @return Argon2 instance.
     */
    public static Argon2 create() {
        return createInternal(Argon2Types.ARGON2i, Argon2Constants.DEFAULT_SALT_LENGTH, Argon2Constants.DEFAULT_HASH_LENGTH);
    }

    /**
     * Creates a new {@link Argon2Advanced} instance.
     *
     * @return Argon2Advanced instance.
     */
    public static Argon2Advanced createAdvanced() {
        return createInternal(Argon2Types.ARGON2i, Argon2Constants.DEFAULT_SALT_LENGTH, Argon2Constants.DEFAULT_HASH_LENGTH);
    }

    /**
     * Creates a new {@link Argon2} instance.
     *
     * @param saltLen Byte length of salt.
     * @param hashLen Byte length of hash.
     * @return Argon2 instance.
     */
    public static Argon2 create(int saltLen, int hashLen) {
        return createInternal(Argon2Types.ARGON2i, saltLen, hashLen);
    }

    /**
     * Creates a new {@link Argon2Advanced} instance.
     *
     * @param saltLen Byte length of salt.
     * @param hashLen Byte length of hash.
     * @return Argon2Advanced instance.
     */
    public static Argon2Advanced createAdvanced(int saltLen, int hashLen) {
        return createInternal(Argon2Types.ARGON2i, saltLen, hashLen);
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
     * @param type    Argon2 type.
     * @param saltLen Byte length of salt.
     * @param hashLen Byte length of hash.
     * @return Argon2Advanced instance.
     */
    public static Argon2Advanced createAdvanced(Argon2Types type, int saltLen, int hashLen) {
        return createInternal(type, saltLen, hashLen);
    }

    /**
     * Creates a new {@link Argon2} instance with the given type.
     *
     * @param type    Argon2 type.
     * @param saltLen Byte length of salt.
     * @param hashLen Byte length of hash.
     * @return Argon2 instance.
     */
    public static Argon2 create(Argon2Types type, int saltLen, int hashLen) {
        return createInternal(type, saltLen, hashLen);
    }

    private static Argon2Advanced createInternal(Argon2Types type, int saltLen, int hashLen) {
        switch (type) {
            case ARGON2i:
                return new Argon2i(saltLen, hashLen);
            case ARGON2d:
                return new Argon2d(saltLen, hashLen);
            case ARGON2id:
                return new Argon2id(saltLen, hashLen);
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

        Argon2Types(int idx) {
            this.jnaType = new Argon2_type(idx);
        }

        public Argon2_type getJnaType() {
            return jnaType;
        }
    }
}
