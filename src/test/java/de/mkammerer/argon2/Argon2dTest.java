package de.mkammerer.argon2;

import de.mkammerer.argon2.base.AbstractArgonTest;

public class Argon2dTest extends AbstractArgonTest {
    @Override
    protected Argon2Advanced createSut() {
        return Argon2Factory.createAdvanced(Argon2Factory.Argon2Types.ARGON2d);
    }

    @Override
    protected String getHashPrefix() {
        return "$argon2d$";
    }
}
