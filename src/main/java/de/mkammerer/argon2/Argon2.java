package de.mkammerer.argon2;

/**
 * Argon2 password hashing function.
 */
public interface Argon2 {
    /**
     * Hashes a password.
     *
     * @param iterations  Number of iterations
     * @param memory      Sets memory usage to x kibibytes
     * @param parallelism Number of threads and compute lanes
     * @param password    Password to hash
     * @return Hashed password.
     */
    String hash(int iterations, int memory, int parallelism, String password);

    /**
     * Verifies a password against a hash.
     *
     * @param hash Hash.
     * @param password Password.
     * @return True if the password matches the hash, false otherwise.
     */
    boolean verify(String hash, String password);
}
