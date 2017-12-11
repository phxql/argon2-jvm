package de.mkammerer.argon2.jna;

import com.sun.jna.IntegerType;

/**
 * uint32_t type for C interaction.
 */
public class JnaUint32 extends IntegerType {
    /**
     * Constructor.
     */
    public JnaUint32() {
        this(0);
    }

    /**
     * Constructor.
     *
     * @param value Value.
     */
    public JnaUint32(int value) {
        super(4, value, true);
    }
}
