package de.mkammerer.argon2.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * JNA bindings for Argon2.
 */
public interface Argon2Library extends Library {
    /**
     * Singleton instance.
     */
    Argon2Library INSTANCE = Native.load("argon2", Argon2Library.class);

    /**
     * Return code if everything is okay.
     */
    int ARGON2_OK = 0;

    /*
    int argon2i_hash_encoded(const uint32_t t_cost, const uint32_t m_cost,
                         const uint32_t parallelism, const void *pwd,
                         const size_t pwdlen, const void *salt,
                         const size_t saltlen, const size_t hashlen,
                         char *encoded, const size_t encodedlen);
     */

    /**
     * Hashes a password with Argon2i, producing an encoded hash.
     *
     * @param t_cost      Number of iterations
     * @param m_cost      Sets memory usage to m_cost kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param pwd         Pointer to password
     * @param pwdlen      Password size in bytes
     * @param salt        Pointer to salt
     * @param saltlen     Salt size in bytes
     * @param hashlen     Desired length of the hash in bytes
     * @param encoded     Buffer where to write the encoded hash
     * @param encodedlen  Size of the buffer (thus max size of the encoded hash)
     * @return {@link #ARGON2_OK} if successful
     */
    int argon2i_hash_encoded(JnaUint32 t_cost, JnaUint32 m_cost, JnaUint32 parallelism, byte[] pwd, Size_t pwdlen, byte[] salt, Size_t saltlen, Size_t hashlen, byte[] encoded, Size_t encodedlen);

    /**
     * Hashes a password with Argon2id, producing an encoded hash.
     *
     * @param t_cost      Number of iterations
     * @param m_cost      Sets memory usage to m_cost kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param pwd         Pointer to password
     * @param pwdlen      Password size in bytes
     * @param salt        Pointer to salt
     * @param saltlen     Salt size in bytes
     * @param hashlen     Desired length of the hash in bytes
     * @param encoded     Buffer where to write the encoded hash
     * @param encodedlen  Size of the buffer (thus max size of the encoded hash)
     * @return {@link #ARGON2_OK} if successful
     */
    int argon2id_hash_encoded(JnaUint32 t_cost, JnaUint32 m_cost, JnaUint32 parallelism, byte[] pwd, Size_t pwdlen, byte[] salt, Size_t saltlen, Size_t hashlen, byte[] encoded, Size_t encodedlen);

    /**
     * Hashes a password with Argon2d, producing an encoded hash.
     *
     * @param t_cost      Number of iterations
     * @param m_cost      Sets memory usage to m_cost kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param pwd         Pointer to password
     * @param pwdlen      Password size in bytes
     * @param salt        Pointer to salt
     * @param saltlen     Salt size in bytes
     * @param hashlen     Desired length of the hash in bytes
     * @param encoded     Buffer where to write the encoded hash
     * @param encodedlen  Size of the buffer (thus max size of the encoded hash)
     * @return {@link #ARGON2_OK} if successful
     */
    int argon2d_hash_encoded(JnaUint32 t_cost, JnaUint32 m_cost, JnaUint32 parallelism, byte[] pwd, Size_t pwdlen, byte[] salt, Size_t saltlen, Size_t hashlen, byte[] encoded, Size_t encodedlen);

    /**
     * Hashes a password with Argon2i, producing an encoded hash.
     *
     * @param t_cost      Number of iterations
     * @param m_cost      Sets memory usage to m_cost kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param pwd         Pointer to password
     * @param pwdlen      Password size in bytes
     * @param salt        Pointer to salt
     * @param saltlen     Salt size in bytes
     * @param hash        Buffer where to write the raw hash
     * @param hashlen     Desired length of the hash in bytes
     * @return {@link #ARGON2_OK} if successful
     */
    int argon2i_hash_raw(JnaUint32 t_cost, JnaUint32 m_cost, JnaUint32 parallelism, byte[] pwd, Size_t pwdlen, byte[] salt, Size_t saltlen, byte[] hash, Size_t hashlen);

    /**
     * Hashes a password with Argon2id, producing an encoded hash.
     *
     * @param t_cost      Number of iterations
     * @param m_cost      Sets memory usage to m_cost kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param pwd         Pointer to password
     * @param pwdlen      Password size in bytes
     * @param salt        Pointer to salt
     * @param saltlen     Salt size in bytes
     * @param hash        Buffer where to write the raw hash
     * @param hashlen     Desired length of the hash in bytes
     * @return {@link #ARGON2_OK} if successful
     */
    int argon2id_hash_raw(JnaUint32 t_cost, JnaUint32 m_cost, JnaUint32 parallelism, byte[] pwd, Size_t pwdlen, byte[] salt, Size_t saltlen, byte[] hash, Size_t hashlen);

    /**
     * Hashes a password with Argon2d, producing an encoded hash.
     *
     * @param t_cost      Number of iterations
     * @param m_cost      Sets memory usage to m_cost kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param pwd         Pointer to password
     * @param pwdlen      Password size in bytes
     * @param salt        Pointer to salt
     * @param saltlen     Salt size in bytes
     * @param hash        Buffer where to write the raw hash
     * @param hashlen     Desired length of the hash in bytes
     * @return {@link #ARGON2_OK} if successful
     */
    int argon2d_hash_raw(JnaUint32 t_cost, JnaUint32 m_cost, JnaUint32 parallelism, byte[] pwd, Size_t pwdlen, byte[] salt, Size_t saltlen, byte[] hash, Size_t hashlen);

    /*
    ARGON2_PUBLIC int argon2_hash(const uint32_t t_cost, const uint32_t m_cost,
                              const uint32_t parallelism, const void *pwd,
                              const size_t pwdlen, const void *salt,
                              const size_t saltlen, void *hash,
                              const size_t hashlen, char *encoded,
                              const size_t encodedlen, argon2_type type,
                              const uint32_t version);
     */
    int argon2_hash(JnaUint32 t_cost, JnaUint32 m_cost, JnaUint32 parallelism, byte[] pwd, Size_t pwdlen, byte[] salt, Size_t saltlen, byte[] hash, Size_t hashlen, byte[] encoded, Size_t encodedlen, Argon2_type type, Argon2_version version);

    /**
     * Verifies a password against an Argon2i encoded string.
     *
     * @param encoded String encoding parameters, salt, hash
     * @param pwd     Pointer to password
     * @param pwdlen  Password size in bytes
     * @return ARGON2_OK if successful
     */
    int argon2i_verify(byte[] encoded, byte[] pwd, Size_t pwdlen);

    /**
     * Verifies a password against an Argon2d encoded string.
     *
     * @param encoded String encoding parameters, salt, hash
     * @param pwd     Pointer to password
     * @param pwdlen  Password size in bytes
     * @return ARGON2_OK if successful
     */
    int argon2d_verify(byte[] encoded, byte[] pwd, Size_t pwdlen);

    /**
     * Verifies a password against an Argon2id encoded string.
     *
     * @param encoded String encoding parameters, salt, hash
     * @param pwd     Pointer to password
     * @param pwdlen  Password size in bytes
     * @return ARGON2_OK if successful
     */
    int argon2id_verify(byte[] encoded, byte[] pwd, Size_t pwdlen);

    /*
    ARGON2_PUBLIC int argon2_verify(const char *encoded, const void *pwd,
                                const size_t pwdlen, argon2_type type);
     */
    int argon2_verify(byte[] encoded, byte[] pwd, Size_t pwdlen, Argon2_type type);

    /**
     * Argon2i: Version of Argon2 that picks memory blocks
     * independent on the password and salt. Good for side-channels,
     * but worse w.r.t. tradeoff attacks if only one pass is used.
     *
     * @param context Pointer to current Argon2 context
     * @return Zero if successful, a non zero error code otherwise
     */
    int argon2i_ctx(Argon2_context.ByReference context);

    /**
     * Argon2d: Version of Argon2 that picks memory blocks depending
     * on the password and salt. Only for side-channel-free
     * environment!!
     *
     * @param contexts Pointer to current Argon2 context
     * @return Zero if successful, a non zero error code otherwise
     */
    int argon2d_ctx(Argon2_context.ByReference contexts);

    /**
     * Argon2id: Version of Argon2 where the first half-pass over memory is
     * password-independent, the rest are password-dependent (on the password and
     * salt). OK against side channels (they reduce to 1/2-pass Argon2i), and
     * better with w.r.t. tradeoff attacks (similar to Argon2d).
     *
     * @param contexts Pointer to current Argon2 context
     * @return Zero if successful, a non zero error code otherwise
     */
    int argon2id_ctx(Argon2_context.ByReference contexts);

    /*
    ARGON2_PUBLIC int argon2_ctx(argon2_context *context, argon2_type type);
     */
    int argon2_ctx(Argon2_context.ByReference context, Argon2_type type);

    /**
     * Verify if a given password is correct for Argon2d hashing
     *
     * @param context Pointer to current Argon2 context
     * @param hash    The password hash to verify. The length of the hash is
     *                specified by the context outlen member
     * @return Zero if successful, a non zero error code otherwise
     */
    int argon2i_verify_ctx(Argon2_context.ByReference context, byte[] hash);

    /**
     * Verify if a given password is correct for Argon2i hashing
     *
     * @param context Pointer to current Argon2 context
     * @param hash    The password hash to verify. The length of the hash is
     *                specified by the context outlen member
     * @return Zero if successful, a non zero error code otherwise
     */
    int argon2d_verify_ctx(Argon2_context.ByReference context, byte[] hash);

    /**
     * Verify if a given password is correct for Argon2id hashing
     *
     * @param context Pointer to current Argon2 context
     * @param hash    The password hash to verify. The length of the hash is
     *                specified by the context outlen member
     * @return Zero if successful, a non zero error code otherwise
     */
    int argon2id_verify_ctx(Argon2_context.ByReference context, byte[] hash);

    /*
    ARGON2_PUBLIC int argon2_verify_ctx(argon2_context *context, const char *hash,
                                        argon2_type type);
    */
    int argon2_verify_ctx(Argon2_context.ByReference contexts, byte[] hash, Argon2_version version);

    /**
     * Returns the encoded hash length for the given input parameters.
     *
     * @param t_cost      Number of iterations.
     * @param m_cost      Memory usage in kibibytes.
     * @param parallelism Number of threads; used to compute lanes.
     * @param saltlen     Salt size in bytes.
     * @param hashlen     Hash size in bytes.
     * @param type        The argon2 type.
     * @return The encoded hash length in bytes.
     */
    Size_t argon2_encodedlen(JnaUint32 t_cost, JnaUint32 m_cost, JnaUint32 parallelism, JnaUint32 saltlen, JnaUint32 hashlen, Argon2_type type);

    /**
     * Get the associated error message for given error code.
     *
     * @param error_code Numeric error code.
     * @return The error message associated with the given error code.
     */
    String argon2_error_message(int error_code);
}
