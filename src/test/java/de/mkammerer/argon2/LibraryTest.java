package de.mkammerer.argon2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LibraryTest {
    @Test
    public void test() throws Exception {
        Argon2 sut = Argon2Factory.create();

        String hash = sut.hash(2, 65536, 1, "password");
        System.out.println(hash);

        assertThat(sut.verify(hash, "password"), is(true));
        assertThat(sut.verify(hash, "not-the-password"), is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidParameters() throws Exception {
        Argon2 sut = Argon2Factory.create();

        sut.hash(0, 0, 0, "password");
    }
}