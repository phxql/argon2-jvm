package de.mkammerer.argon2;

import java.nio.charset.Charset;

/**
 * Advanced Argon2 hash functions.
 * <p>
 * All implementing classes need to be thread safe.
 */
public interface Argon2Advanced extends Argon2 {
    /**
     * Hashes a password and returns the raw bytes.
     * <p>
     * Uses UTF-8 encoding.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to hash
     * @param salt        Salt to use. This will override the default salt length
     * @return Hashed password in raw bytes.
     */
    byte[] rawHash(int iterations, int memory, int parallelism, String password, byte[] salt);

    /**
     * Hashes a password and returns the raw bytes.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to hash
     * @param charset     Charset of the password
     * @param salt        Salt to use. This will override the default salt length
     * @return Hashed password in raw bytes.
     */
    byte[] rawHash(int iterations, int memory, int parallelism, String password, Charset charset, byte[] salt);

    /**
     * Hashes a password and returns the raw bytes.
     * <p>
     * Uses UTF-8 encoding.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to hash
     * @param salt        Salt to use. This will override the default salt length
     * @return Hashed password in raw bytes.
     */
    byte[] rawHash(int iterations, int memory, int parallelism, char[] password, byte[] salt);

    /**
     * Hashes a password and returns the raw bytes.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to hash
     * @param charset     Charset of the password
     * @param salt        Salt to use. This will override the default salt length
     * @return Hashed password in raw bytes.
     */
    byte[] rawHash(int iterations, int memory, int parallelism, char[] password, Charset charset, byte[] salt);

    /**
     * Hashes a password, using the given salt.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to hash
     * @param charset     Charset of the password
     * @param salt        Salt
     * @return Hashed password.
     */
    String hash(int iterations, int memory, int parallelism, char[] password, Charset charset, byte[] salt);
}
