package de.mkammerer.argon2.compatibility;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class App {
    public static void main(String[] args) {
        System.out.println("Running test ...");
        test();
        System.out.println("Test successful!");
    }

    private static void test() {
        Argon2 argon2 = Argon2Factory.create();

        // Hash password
        String hash = argon2.hash(10, 65536, 1, "password".toCharArray());

        if (!argon2.verify(hash, "password".toCharArray())) {
            throw new AssertionError("Hashes which should match don't match!");
        }

        if (argon2.verify(hash, "not-the-password".toCharArray())) {
            throw new AssertionError("Hashes which shouldn't match match!");
        }
    }
}
