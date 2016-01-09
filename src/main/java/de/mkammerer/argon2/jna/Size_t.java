package de.mkammerer.argon2.jna;

import com.sun.jna.IntegerType;
import com.sun.jna.Native;

/**
 * size_t type for C interaction.
 */
public class Size_t extends IntegerType {
    /**
     * Constructor.
     */
    public Size_t() {
        this(0);
    }

    /**
     * Constructor.
     *
     * @param value Value.
     */
    public Size_t(long value) {
        super(Native.SIZE_T_SIZE, value);
    }
}
