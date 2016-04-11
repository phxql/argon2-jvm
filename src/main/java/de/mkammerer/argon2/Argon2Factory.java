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

    public enum Argon2Types {
      ARGON2i,
      ARGON2d
    }

    /**
     * Creates a new {@link Argon2} instance.
     *
     * @return Argon2 instance.
     */
    public static Argon2 create() {
        return create(Argon2Types.ARGON2i);
    }

    public static Argon2 create(Argon2Types type) {
      switch (type) {
        case ARGON2i:
          return new Argon2i();
        case ARGON2d:
          return new Argon2d();
        default:
          throw new IllegalArgumentException("Invalid argon2 type");
      }
    }
}
