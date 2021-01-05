package de.mkammerer.argon2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class Argon2FactoryTest {
    @Test
    public void testDefault() {
        Argon2 argon2 = Argon2Factory.create();
        assertThat(argon2, instanceOf(Argon2i.class));
    }

    @Test
    public void testAdvancedDefault() {
        Argon2 argon2 = Argon2Factory.createAdvanced();
        assertThat(argon2, instanceOf(Argon2i.class));
    }

    @Test
    public void testArgon2i() {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
        assertThat(argon2, instanceOf(Argon2i.class));
    }

    @Test
    public void testAdvancedArgon2i() {
        Argon2 argon2 = Argon2Factory.createAdvanced(Argon2Factory.Argon2Types.ARGON2i);
        assertThat(argon2, instanceOf(Argon2i.class));
    }

    @Test
    public void testArgon2d() {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
        assertThat(argon2, instanceOf(Argon2d.class));
    }

    @Test
    public void testAdvancedArgon2d() {
        Argon2 argon2 = Argon2Factory.createAdvanced(Argon2Factory.Argon2Types.ARGON2d);
        assertThat(argon2, instanceOf(Argon2d.class));
    }

    @Test
    public void testArgon2id() {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        assertThat(argon2, instanceOf(Argon2id.class));
    }

    @Test
    public void testAdvancedArgon2id() {
        Argon2 argon2 = Argon2Factory.createAdvanced(Argon2Factory.Argon2Types.ARGON2id);
        assertThat(argon2, instanceOf(Argon2id.class));
    }
}