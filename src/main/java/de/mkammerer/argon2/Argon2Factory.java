package de.mkammerer.argon2;

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
        return create(Argon2Constants.Argon2Types.ARGON2i, Argon2Constants.DEFAULT_SALT_LENGTH, Argon2Constants.DEFAULT_HASH_LENGTH);
    }

    /**
     * Creates a new {@link Argon2} instance.
     *
     * @param saltLen Byte length of salt.
     * @param hashLen Byte length of hash.
     * @return Argon2 instance.
     */
    public static Argon2 create(int saltLen, int hashLen) {
        return create(Argon2Constants.Argon2Types.ARGON2i, saltLen, hashLen);
    }

    /**
     * Creates a new {@link Argon2} instance with the given type.
     *
     * @param type Argon2 type.
     * @return Argon2 instance.
     */
    public static Argon2 create(Argon2Constants.Argon2Types type) {
        return create(type, Argon2Constants.DEFAULT_SALT_LENGTH, Argon2Constants.DEFAULT_HASH_LENGTH);
    }

    /**
     * Creates a new {@link Argon2} instance with the given type.
     *
     * @param type Argon2 type.
     * @param saltLen Byte length of salt.
     * @param hashLen Byte length of hash.
     * @return Argon2 instance.
     */
    public static Argon2 create(Argon2Constants.Argon2Types type, int saltLen, int hashLen) {
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
}
