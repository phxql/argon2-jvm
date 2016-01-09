package de.mkammerer.argon2.jna;

import com.sun.jna.IntegerType;

/**
 * uint32_t type for C interaction.
 */
public class Uint32_t extends IntegerType {
    /**
     * Constructor.
     */
    public Uint32_t() {
        this(0);
    }

    /**
     * Constructor.
     *
     * @param value Value.
     */
    public Uint32_t(int value) {
        super(4, value, true);
    }
}
