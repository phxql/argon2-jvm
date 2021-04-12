package de.mkammerer.argon2;

import de.mkammerer.argon2.jna.Argon2_version;

/**
 * Version of the Argon2 algorithm.
 */
public enum Argon2Version {
    V10(0x10),
    V13(0x13),
    DEFAULT_VERSION(V13.version);

    private final int version;
    private final Argon2_version jnaType;

    Argon2Version(int version) {
        this.version = version;
        this.jnaType = new Argon2_version(version);
    }

    public Argon2_version getJnaType() {
        return jnaType;
    }

    public int getVersion() {
        return version;
    }
}
