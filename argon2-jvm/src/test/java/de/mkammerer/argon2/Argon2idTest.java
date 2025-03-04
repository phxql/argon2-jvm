package de.mkammerer.argon2;

import de.mkammerer.argon2.base.AbstractArgonTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Argon2idTest extends AbstractArgonTest {
    @Override
    protected Argon2Advanced createSut() {
        return Argon2Factory.createAdvanced(Argon2Factory.Argon2Types.ARGON2id);
    }

    @Override
    protected String getHashPrefix() {
        return "$argon2id$";
    }

    @Test
    public void testHashWithSalt() {
        String hash = getSut().hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), UTF8, getFixedSalt());

        assertThat(hash).isEqualTo("$argon2id$v=19$m=1024,t=2,p=1$dGhpc2lzdGhlc2FsdA$9GrHfosTNz04GroO1Gx0gTT3F9c3X6X8ztZgESUQzZI");
    }

    @Test
    public void testNeedsRehash() {
        assertThat(getSut().needsRehash("$argon2id$v=19$m=1024,t=2,p=1$dGhpc2lzdGhlc2FsdA$9GrHfosTNz04GroO1Gx0gTT3F9c3X6X8ztZgESUQzZI", ITERATIONS, MEMORY, PARALLELISM)).isFalse();
        assertThat(getSut().needsRehash("$argon2id$v=19$m=2048,t=3,p=2$dGhpc2lzdGhlc2FsdA$9GrHfosTNz04GroO1Gx0gTT3F9c3X6X8ztZgESUQzZI", ITERATIONS, MEMORY, PARALLELISM)).isFalse();
        assertThat(getSut().needsRehash("$argon2id$v=19$m=1024,t=1,p=1$dGhpc2lzdGhlc2FsdA$9GrHfosTNz04GroO1Gx0gTT3F9c3X6X8ztZgESUQzZI", ITERATIONS, MEMORY, PARALLELISM)).isTrue();
    }
}
