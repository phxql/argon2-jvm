package de.mkammerer.argon2;

import de.mkammerer.argon2.base.AbstractArgonTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Argon2iTest extends AbstractArgonTest {
    @Override
    protected Argon2Advanced createSut() {
        return Argon2Factory.createAdvanced(Argon2Factory.Argon2Types.ARGON2i);
    }

    @Override
    protected String getHashPrefix() {
        return "$argon2i$";
    }

    @Test
    public void testHashWithSalt() {
        String hash = getSut().hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), UTF8, getFixedSalt());

        assertThat(hash).isEqualTo("$argon2i$v=19$m=1024,t=2,p=1$dGhpc2lzdGhlc2FsdA$kIaBJZgiRxlQSTodxlztJK0wornqf0gvK/g0dNrcXpw");
    }

    @Test
    public void testNeedsRehash() {
        assertThat(getSut().needsRehash("$argon2i$v=19$m=1024,t=2,p=1$dGhpc2lzdGhlc2FsdA$kIaBJZgiRxlQSTodxlztJK0wornqf0gvK/g0dNrcXpw", ITERATIONS, MEMORY, PARALLELISM)).isFalse();
        assertThat(getSut().needsRehash("$argon2i$v=19$m=2048,t=3,p=2$dGhpc2lzdGhlc2FsdA$kIaBJZgiRxlQSTodxlztJK0wornqf0gvK/g0dNrcXpw", ITERATIONS, MEMORY, PARALLELISM)).isFalse();
        assertThat(getSut().needsRehash("$argon2i$v=19$m=1024,t=1,p=1$dGhpc2lzdGhlc2FsdA$kIaBJZgiRxlQSTodxlztJK0wornqf0gvK/g0dNrcXpw", ITERATIONS, MEMORY, PARALLELISM)).isTrue();
    }
}
