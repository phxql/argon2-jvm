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
        return new Argon2i();
    }
}
