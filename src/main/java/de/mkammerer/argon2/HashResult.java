package de.mkammerer.argon2;

/**
 * Hash result, containing both the raw and the encoded representation.
 */
public class HashResult {
    private final byte[] raw;
    private final String encoded;

    public HashResult(byte[] raw, String encoded) {
        this.raw = raw;
        this.encoded = encoded;
    }

    public byte[] getRaw() {
        return raw;
    }

    public String getEncoded() {
        return encoded;
    }
}
