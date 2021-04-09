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
     * @deprecated Use the {@link #rawHash(int, int, int, char[], byte[])} method instead. Will be removed in version 3.
     */
    @Deprecated
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
     * @deprecated Use the {@link #hash(int, int, int, char[], Charset)} method instead. Will be removed in version 3.
     */
    @Deprecated
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
     * Hashes the given data and returns the raw bytes.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param data        Data to hash
     * @param salt        Salt to use. This will override the default salt length
     * @return Hashed data in raw bytes.
     */
    byte[] rawHash(int iterations, int memory, int parallelism, byte[] data, byte[] salt);

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
     * Uses the given password to generate key material (password based key derivation).
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to generate key material from
     * @param charset     Charset of the password
     * @param salt        Salt to use. This will override the default salt length
     * @param keyLength   Length of the returned key material in bytes.
     * @return Key material.
     */
    byte[] pbkdf(int iterations, int memory, int parallelism, char[] password, Charset charset, byte[] salt, int keyLength);

    /**
     * Uses the given password to generate key material (password based key derivation).
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to generate key material from
     * @param salt        Salt to use. This will override the default salt length
     * @param keyLength   Length of the returned key material in bytes.
     * @return Key material.
     */
    byte[] pbkdf(int iterations, int memory, int parallelism, byte[] password, byte[] salt, int keyLength);


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

    /**
     * Advanced version of hash, let the caller specify the hash length, the salt and the argon2 version. Returns both
     * the encoded and the raw hash.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to hash
     * @param salt        Salt to use. This will override the default salt length
     * @param hashLength  Length of the returned hash in bytes.
     * @param version     Argon2 version
     * @return Hash result, containing both the raw and the encoded representation.
     */
    HashResult hashAdvanced(int iterations, int memory, int parallelism, byte[] password, byte[] salt, int hashLength, Argon2Version version);

    /**
     * Hashes a password, using the given salt, secret and associated data.
     *
     * @param iterations     Number of iterations
     * @param memory         Sets memory usage to x kibibytes
     * @param parallelism    Number of threads and compute lanes
     * @param password       Password to hash
     * @param charset        Charset of the password
     * @param salt           Salt
     * @param secret         Secret (sometimes referred as Pepper)
     * @param associatedData Associated Data
     * @return Hashed password in raw bytes.
     */
    byte[] rawHashAdvanced(int iterations, int memory, int parallelism, char[] password, Charset charset, byte[] salt, byte[] secret, byte[] associatedData);

    /**
     * Advanced version of hash, let the caller specify addition parameters such as hash length, salt, secret and associated data.
     * Return both the encoded and the raw hash.
     *
     * @param iterations     Number of iterations
     * @param memory         Sets memory usage to x kibibytes
     * @param parallelism    Number of threads and compute lanes
     * @param password       Password to hash
     * @param salt           Salt
     * @param secret         Secret (sometimes referred as Pepper)
     * @param associatedData Associated Data
     * @param hashLength     Length of the returned hash in bytes.
     * @param version        Argon2 version
     * @return Hashed password in raw bytes.
     */
    byte[] rawHashAdvanced(int iterations, int memory, int parallelism, byte[] password, byte[] salt, byte[] secret, byte[] associatedData, int hashLength, Argon2Version version);

    /**
     * Verifies a password against a hash.
     *
     * @param iterations     Number of iterations
     * @param memory         Sets memory usage to x kibibytes
     * @param parallelism    Number of threads and compute lanes
     * @param password       Password to hash
     * @param charset        Charset of the password
     * @param salt           Salt
     * @param secret         Secret (sometimes referred as Pepper)
     * @param associatedData Associated Data
     * @param rawHash        Raw Hash bytes.
     * @return True if the password matches the hash, false otherwise.
     */
    boolean verifyAdvanced(int iterations, int memory, int parallelism, char[] password, Charset charset, byte[] salt, byte[] secret, byte[] associatedData, byte[] rawHash);

    /**
     * Verifies a password against a hash.
     *
     * @param iterations     Number of iterations
     * @param memory         Sets memory usage to x kibibytes
     * @param parallelism    Number of threads and compute lanes
     * @param password       Password to hash
     * @param salt           Salt
     * @param secret         Secret (sometimes referred as Pepper)
     * @param associatedData Associated Data
     * @param hashLength     Length of the returned hash in bytes.
     * @param version        Argon2 version
     * @param rawHash        Raw Hash bytes.
     * @return True if the password matches the hash, false otherwise.
     */
    boolean verifyAdvanced(int iterations, int memory, int parallelism, byte[] password, byte[] salt, byte[] secret, byte[] associatedData, int hashLength, Argon2Version version, byte[] rawHash);

    /**
     * Generates salt with the default length.
     *
     * @return Salt.
     */
    byte[] generateSalt();

    /**
     * Generates salt with the specified length in bytes.
     *
     * @param lengthInBytes Length of salt in bytes.
     * @return Salt.
     */
    byte[] generateSalt(int lengthInBytes);
}
