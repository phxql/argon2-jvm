package de.mkammerer.argon2;

/**
 * Version of the Argon2 algorithm.
 */
public enum Argon2Version {
    V10(0x10),
    V13(0x13);

    private final int jnaVersion;

    Argon2Version(int jnaVersion) {
        this.jnaVersion = jnaVersion;
    }

    public int getJnaVersion() {
        return jnaVersion;
    }
}
