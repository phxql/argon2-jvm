package de.mkammerer.argon2;

public interface Argon2 {
    // String hashEncoded(int iterations, int memory, int parallelism, byte[] password, byte[] salt, int hashLength);

    String hash(int iterations, int memory, int parallelism, String password);

    boolean verify(String hash, String password);
}
