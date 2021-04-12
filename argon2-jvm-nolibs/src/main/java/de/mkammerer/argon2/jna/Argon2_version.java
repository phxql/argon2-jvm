package de.mkammerer.argon2.jna;

import de.mkammerer.argon2.Argon2Version;

/**
 * argon2_type type for C interaction.
 */
public class Argon2_version extends JnaUint32 {

    /**
     * Constructor.
     */
    public Argon2_version() {
        this(Argon2Version.DEFAULT_VERSION.getVersion());
    }

    /**
     * Constructor.
     *
     * @param version Version.
     */
    public Argon2_version(int version) {
        super(version);
    }
}
