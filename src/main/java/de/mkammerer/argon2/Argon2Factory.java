package de.mkammerer.argon2;

public final class Argon2Factory {
    private Argon2Factory() {
    }

    public static Argon2 create() {
        return new Argon2i();
    }
}
