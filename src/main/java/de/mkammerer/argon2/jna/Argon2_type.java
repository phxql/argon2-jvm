package de.mkammerer.argon2.jna;

import com.sun.jna.NativeLong;

/**
 * argon2_type type for C interaction.
 */
public class Argon2_type extends NativeLong {
    /**
     * Constructor.
     */
    public Argon2_type() {
        this(0);
    }

    /**
     * Constructor.
     *
     * @param value Value.
     */
    public Argon2_type(long value) {
        super(value);
    }
}
