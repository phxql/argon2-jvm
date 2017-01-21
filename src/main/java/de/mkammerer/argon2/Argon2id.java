package de.mkammerer.argon2;

import de.mkammerer.argon2.jna.Argon2Library;
import de.mkammerer.argon2.jna.Size_t;
import de.mkammerer.argon2.jna.Uint32_t;

/**
 * Argon2i password hashing function.
 */
class Argon2id extends BaseArgon2 {
    @Override
    public final Argon2Constants.Argon2Types getType() {
        return Argon2Constants.Argon2Types.ARGON2id;
    }

    /**
     * Constructor.
     * <p>
     * Uses default salt and hash lengths.
     */
    public Argon2id() {
    }

    /**
     * Constructor.
     *
     * @param saltLen Salt length in bytes.
     * @param hashLen Hash length in bytes.
     */
    public Argon2id(int saltLen, int hashLen) {
        super(saltLen, hashLen);
    }

    @Override
    protected int callLibraryHash(byte[] pwd, byte[] salt, Uint32_t iterations, Uint32_t memory, Uint32_t parallelism, byte[] encoded) {
        return Argon2Library.INSTANCE.argon2id_hash_encoded(
                iterations, memory, parallelism, pwd, new Size_t(pwd.length),
                salt, new Size_t(salt.length), new Size_t(getHashLength()), encoded, new Size_t(encoded.length)
        );
    }

    @Override
    protected int callLibraryVerify(byte[] encoded, byte[] pwd) {
        return Argon2Library.INSTANCE.argon2id_verify(encoded, pwd, new Size_t(pwd.length));
    }
}
