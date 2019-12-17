package de.mkammerer.argon2;

import de.mkammerer.argon2.jna.Argon2Library;
import de.mkammerer.argon2.jna.JnaUint32;
import de.mkammerer.argon2.jna.Size_t;

/**
 * Argon2i password hashing function.
 * <p>
 * This class is thread safe.
 */
class Argon2id extends BaseArgon2 {
    /**
     * Constructor.
     *
     * @param defaultSaltLength Default salt length in bytes. Can be overridden by some methods.
     * @param defaultHashLength Default hash length in bytes. Can be overridden by some methods.
     */
    Argon2id(int defaultSaltLength, int defaultHashLength) {
        super(defaultSaltLength, defaultHashLength);
    }

    @Override
    public final Argon2Factory.Argon2Types getType() {
        return Argon2Factory.Argon2Types.ARGON2id;
    }

    @Override
    protected int callLibraryHash(byte[] pwd, byte[] salt, JnaUint32 iterations, JnaUint32 memory, JnaUint32 parallelism, byte[] encoded) {
        return Argon2Library.INSTANCE.argon2id_hash_encoded(
                iterations, memory, parallelism, pwd, new Size_t(pwd.length),
                salt, new Size_t(salt.length), new Size_t(getDefaultHashLength()), encoded, new Size_t(encoded.length)
        );
    }

    @Override
    protected int callLibraryRawHash(byte[] pwd, byte[] salt, JnaUint32 iterations, JnaUint32 memory, JnaUint32 parallelism, byte[] hash) {
        return Argon2Library.INSTANCE.argon2id_hash_raw(
                iterations, memory, parallelism, pwd, new Size_t(pwd.length),
                salt, new Size_t(salt.length), hash, new Size_t(hash.length)
        );
    }

    @Override
    protected int callLibraryVerify(byte[] encoded, byte[] pwd) {
        return Argon2Library.INSTANCE.argon2id_verify(encoded, pwd, new Size_t(pwd.length));
    }
}
