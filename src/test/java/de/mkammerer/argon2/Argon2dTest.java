package de.mkammerer.argon2;

import de.mkammerer.argon2.base.AbstractArgonTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Argon2dTest extends AbstractArgonTest {
    @Override
    protected Argon2Advanced createSut() {
        return Argon2Factory.createAdvanced(Argon2Factory.Argon2Types.ARGON2d);
    }

    @Override
    protected String getHashPrefix() {
        return "$argon2d$";
    }

    @Test
    public void testHashWithSalt() {
        String hash = getSut().hash(ITERATIONS, MEMORY, PARALLELISM, PASSWORD.toCharArray(), UTF8, getFixedSalt());

        assertThat(hash, is("$argon2d$v=19$m=1024,t=2,p=1$dGhpc2lzdGhlc2FsdA$rkBsbLVYkXDowmxcwZ9qjpLuAbNjIPBXxmD27YvzHzw"));
    }
}
