package de.mkammerer.argon2.runner;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runner main class.
 */
public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    /**
     * Main entry point.
     *
     * @param args application arguments
     */
    public static void main(String[] args) {
        LOGGER.info("Starting...");
        int exitCode = 0;
        try {
            new Runner().run(args);
        } catch (Exception e) {
            LOGGER.error("Unhandled exception", e);
            exitCode = 1;
        } finally {
            LOGGER.info("Stopped");
        }
        System.exit(exitCode);
    }

    private void run(String[] args) {
        LOGGER.info("Creating Argon2 instance");
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        // Read password from user
        char[] password = "my-password".toCharArray();

        try {
            LOGGER.info("Hashing password...");
            // Hash password
            String hash = argon2.hash(10, 65536, 1, password);
            LOGGER.info("Hash: {}", hash);

            LOGGER.info("Verifying password...");
            // Verify password
            if (argon2.verify(hash, password)) {
                // Hash matches password
                LOGGER.info("Verification successful!");
            } else {
                // Hash doesn't match password
                LOGGER.error("Verification failed!");
            }
        } finally {
            LOGGER.info("Wiping confidential data...");
            // Wipe confidential data
            argon2.wipeArray(password);
        }
    }
}
