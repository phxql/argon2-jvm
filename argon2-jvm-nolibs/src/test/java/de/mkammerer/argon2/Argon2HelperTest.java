package de.mkammerer.argon2;

import org.junit.Test;

public class Argon2HelperTest {
    @Test
    public void test() {
        Argon2 argon2 = Argon2Factory.create();
        int iterations = Argon2Helper.findIterations(argon2, 200, 65536, 1, new ConsoleLogger());

        System.out.println("Iterations: " + iterations);
    }

    private static class ConsoleLogger implements Argon2Helper.IterationLogger {
        @Override
        public void log(int iterations, long millisecs) {
            System.out.println(iterations + " iteration(s) took " + millisecs + " ms");
        }
    }
}