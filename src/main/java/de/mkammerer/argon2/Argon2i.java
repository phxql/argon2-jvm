package de.mkammerer.argon2;

import de.mkammerer.argon2.jna.Argon2Library;
import de.mkammerer.argon2.jna.Size_t;
import de.mkammerer.argon2.jna.Uint32_t;

import java.nio.charset.Charset;
import java.security.SecureRandom;

class Argon2i implements Argon2 {
    public static final int SALT_LENGTH = 16;
    public static final int HASH_LENGTH = 32;

    private final SecureRandom secureRandom = new SecureRandom();

    private static final Charset ASCII = Charset.forName("ASCII");

    @Override
    public String hash(int iterations, int memory, int parallelism, String password) {
        byte[] pwd = password.getBytes();
        byte[] salt = generateSalt();

        byte[] encoded = new byte[HASH_LENGTH * 4];

        int result = Argon2Library.INSTANCE.argon2i_hash_encoded(
                new Uint32_t(iterations), new Uint32_t(memory), new Uint32_t(parallelism), pwd, new Size_t(pwd.length),
                salt, new Size_t(salt.length), new Size_t(HASH_LENGTH), encoded, new Size_t(encoded.length)
        );
        if (result != Argon2Library.ARGON2_OK) {
            throw new IllegalStateException("Expected return code " + Argon2Library.ARGON2_OK + ", got " + result + ". See https://github.com/P-H-C/phc-winner-argon2/blob/master/src/argon2.h for return codes.");
        }

        return new String(encoded, ASCII);
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);

        return salt;
    }

    @Override
    public boolean verify(String hash, String password) {
        byte[] encoded = hash.getBytes(ASCII);
        byte[] pwd = password.getBytes();

        int result = Argon2Library.INSTANCE.argon2i_verify(encoded, pwd, new Size_t(pwd.length));

        return result == Argon2Library.ARGON2_OK;
    }
}
