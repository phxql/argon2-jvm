package de.mkammerer.argon2;

/**
 * Provides useful helper methods to work with Argon2.
 */
public final class Argon2Helper {
    /**
     * Nanoseconds in a millisecond.
     */
    private static final long MILLIS_IN_NANOS = 1000000L;
    /**
     * Number of warmup runs.
     */
    private static final int WARMUP_RUNS = 10;
    /**
     * Minimum number of iterations.
     */
    private static final int MIN_ITERATIONS = 1;
    /**
     * Minimum number of memory.
     */
    private static final int MIN_MEMORY = 8;
    /**
     * Minimm number of parallelism.
     */
    private static final int MIN_PARALLELISM = 1;

    /**
     * No instances allowed.
     */
    private Argon2Helper() {
    }

    /**
     * Finds the number of iterations so that the hash function takes at most the given number of milliseconds.
     *
     * @param argon2       Argon2 instance.
     * @param maxMillisecs Maximum number of milliseconds the hash function must take.
     * @param memory       Memory. See {@link Argon2#hash(int, int, int, char[])}.
     * @param parallelism  Parallelism. See {@link Argon2#hash(int, int, int, char[])}.
     * @return The number of iterations so that the hash function takes at most the given number of milliseconds.
     */
    public static int findIterations(Argon2 argon2, long maxMillisecs, int memory, int parallelism) {
        return findIterations(argon2, maxMillisecs, memory, parallelism, new NoopLogger());
    }

    /**
     * Finds the number of iterations so that the hash function takes at most the given number of milliseconds.
     *
     * @param argon2       Argon2 instance.
     * @param maxMillisecs Maximum number of milliseconds the hash function must take.
     * @param memory       Memory. See {@link Argon2#hash(int, int, int, char[])}.
     * @param parallelism  Parallelism. See {@link Argon2#hash(int, int, int, char[])}.
     * @param logger       Logger which gets called with the runtime of the tested iteration steps.
     * @return The number of iterations so that the hash function takes at most the given number of milliseconds.
     */
    public static int findIterations(Argon2 argon2, long maxMillisecs, int memory, int parallelism, IterationLogger logger) {
        char[] password = "password".toCharArray();

        warmup(argon2, password);

        long took;
        int iterations = 0;
        do {
            iterations++;
            long start = System.nanoTime() / MILLIS_IN_NANOS;
            argon2.hash(iterations, memory, parallelism, password);
            long end = System.nanoTime() / MILLIS_IN_NANOS;
            took = end - start;

            logger.log(iterations, took);
        } while (took <= maxMillisecs);

        return iterations - 1;
    }

    /**
     * Calls Argon2 a number of times to warm up the JIT
     *
     * @param argon2   Argon2 instance.
     * @param password Some password.
     */
    private static void warmup(Argon2 argon2, char[] password) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            argon2.hash(MIN_ITERATIONS, MIN_MEMORY, MIN_PARALLELISM, password);
        }
    }

    /**
     * Logger for the iteration tests.
     */
    public interface IterationLogger {
        /**
         * Is called after a hash call is done.
         *
         * @param iterations Number of iterations used.
         * @param millisecs  Time the hash call took in milliseconds.
         */
        void log(int iterations, long millisecs);
    }

    /**
     * Logs nothing.
     */
    public static class NoopLogger implements IterationLogger {
        @Override
        public void log(int iterations, long millisecs) {
            // Do nothing
        }
    }
}
