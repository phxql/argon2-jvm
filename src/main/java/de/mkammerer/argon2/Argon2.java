package de.mkammerer.argon2;

import java.nio.charset.Charset;

/**
 * Argon2 password hashing function.
 * <p>
 * All implementing classes need to be thread safe.
 */
public interface Argon2 {
    /**
     * Hashes a password.
     * <p>
     * Uses UTF-8 encoding.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to hash
     * @return Hashed password.
     * @deprecated Use the {@link #hash(int, int, int, char[])} method instead. Will be removed in version 3.
     */
    @Deprecated
    String hash(int iterations, int memory, int parallelism, String password);

    /**
     * Hashes a password.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to hash
     * @param charset     Charset of the password
     * @return Hashed password.
     * @deprecated Use the {@link #hash(int, int, int, char[], Charset)} method instead. Will be removed in version 3.
     */
    @Deprecated
    String hash(int iterations, int memory, int parallelism, String password, Charset charset);

    /**
     * Hashes a password.
     * <p>
     * Uses UTF-8 encoding.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to hash
     * @return Hashed password.
     */
    String hash(int iterations, int memory, int parallelism, char[] password);

    /**
     * Hashes a password.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to hash
     * @param charset     Charset of the password
     * @return Hashed password.
     */
    String hash(int iterations, int memory, int parallelism, char[] password, Charset charset);

    /**
     * Hashes the given data.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param data        Data to hash
     * @return Hashed data.
     */
    String hash(int iterations, int memory, int parallelism, byte[] data);

    /**
     * Verifies a password against a hash.
     * <p>
     * Uses UTF-8 encoding.
     *
     * @param hash     Hash.
     * @param password Password.
     * @return True if the password matches the hash, false otherwise.
     * @deprecated Use the {@link #verify(String, char[])} method instead. Will be removed in version 3.
     */
    @Deprecated
    boolean verify(String hash, String password);

    /**
     * Verifies a password against a hash.
     *
     * @param hash     Hash.
     * @param password Password.
     * @param charset  Charset of the password
     * @return True if the password matches the hash, false otherwise.
     * @deprecated Use the {@link #verify(String, char[], Charset)} method instead. Will be removed in version 3.
     */
    @Deprecated
    boolean verify(String hash, String password, Charset charset);

    /**
     * Verifies a password against a hash.
     * <p>
     * Uses UTF-8 encoding.
     *
     * @param hash     Hash.
     * @param password Password.
     * @return True if the password matches the hash, false otherwise.
     */
    boolean verify(String hash, char[] password);

    /**
     * Verifies a password against a hash.
     *
     * @param hash     Hash.
     * @param password Password.
     * @param charset  Charset of the password
     * @return True if the password matches the hash, false otherwise.
     */
    boolean verify(String hash, char[] password, Charset charset);

    /**
     * Verifies the given data against a hash.
     *
     * @param hash Hash.
     * @param data Data.
     * @return True if the data matches the hash, false otherwise.
     */
    boolean verify(String hash, byte[] data);

    /**
     * Wipes the data from the given array.
     * <p>
     * Use this method after hash creation / verification on the array which contains the user password.
     *
     * @param array The array to wipe.
     */
    void wipeArray(char[] array);

    /**
     * Wipes the data from the given array.
     * <p>
     * Use this method after hash creation / verification on the array which contains the user password.
     *
     * @param array The array to wipe.
     */
    void wipeArray(byte[] array);

    /**
     * Checks if the given hash uses obsolete parameters like iterations, memory, parallelism.
     * <p>
     * If one of the hash's parameters doesn't match the required minimum, this method will return true.
     *
     * @param hash        a hash created by {@link #hash(int, int, int, char[], java.nio.charset.Charset) hash}
     * @param iterations  number of minimum iterations
     * @param memory      minimum memory usage
     * @param parallelism minimum of parallelism
     * @return {@code true} if the hash should be rehashed to match the given parameters, or {@code false} otherwise.
     * @throws IllegalArgumentException if the hash is invalid
     */
    boolean needsRehash(String hash, int iterations, int memory, int parallelism);
}
