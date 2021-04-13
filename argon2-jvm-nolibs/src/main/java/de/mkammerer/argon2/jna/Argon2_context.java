package de.mkammerer.argon2.jna;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import static com.sun.jna.Structure.FieldOrder;

/**
 * Argon2_context for C interaction.
 */
@FieldOrder({"out", "outlen", "pwd", "pwdlen", "salt", "saltlen", "secret", "secretlen", "ad", "adlen", "t_cost", "m_cost", "lanes", "threads", "version", "allocate_cbk", "free_cbk", "flags"})
public class Argon2_context extends Structure {

    /**
     * output array
     */
    public Pointer out = null;
    /**
     * digest length
     */
    public JnaUint32 outlen = new JnaUint32(0);
    /**
     * password array
     */
    public Pointer pwd = null;
    /**
     * password length
     */
    public JnaUint32 pwdlen = new JnaUint32(0);
    /**
     * salt array
     */
    public Pointer salt = null;
    /**
     * salt length
     */
    public JnaUint32 saltlen = new JnaUint32(0);
    /**
     * key array
     */
    public Pointer secret = Pointer.NULL;
    /**
     * key length
     */
    public JnaUint32 secretlen = new JnaUint32(0);
    /**
     * associated data array
     */
    public Pointer ad = Pointer.NULL;
    /**
     * associated data length
     */
    public JnaUint32 adlen = new JnaUint32(0);
    /**
     * number of passes
     */
    public JnaUint32 t_cost;
    /**
     * amount memory of requested (KB)
     */
    public JnaUint32 m_cost;
    /**
     * number of lanes
     */
    public JnaUint32 lanes;
    /**
     * maximum number of threads
     */
    public JnaUint32 threads;
    /**
     * version number
     */
    public Argon2_version version;
    /**
     * pointer to memory allocator
     */
    public Pointer allocate_cbk = Pointer.NULL;
    /**
     * pointer to memory deallocator
     */
    public Pointer free_cbk = Pointer.NULL;
    /**
     * array of bool options
     */
    public JnaUint32 flags = new JnaUint32(0);

    public static class ByReference extends Argon2_context implements Structure.ByReference {
        // empty body
    }
}
