package de.mkammerer.argon2;

import java.nio.charset.Charset;

/**
 * Argon2 password hashing function.
 */
public interface Argon2 {

    /**
     * Hashes a password.
     *
     * Uses UTF-8 encoding.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to hash
     * @return Hashed password.
     */
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
     */
    String hash(int iterations, int memory, int parallelism, String password, Charset charset);

    /**
     * Hashes a password.
     *
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
     * Verifies a password against a hash.
     *
     * Uses UTF-8 encoding.
     *
     * @param hash     Hash.
     * @param password Password.
     * @return True if the password matches the hash, false otherwise.
     */
    boolean verify(String hash, String password);

    /**
     * Verifies a password against a hash.
     *
     * @param hash     Hash.
     * @param password Password.
     * @param charset  Charset of the password
     * @return True if the password matches the hash, false otherwise.
     */
    boolean verify(String hash, String password, Charset charset);

    /**
     * Verifies a password against a hash.
     *
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
     * Wipes the data from the given array.
     * <p>
     * Use this method after hash creation / verification on the array which contains the user password.
     *
     * @param array The array to wipe.
     */
    void wipeArray(char[] array);
}
