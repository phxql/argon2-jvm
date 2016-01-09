package de.mkammerer.argon2.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface Argon2Library extends Library {
    Argon2Library INSTANCE = (Argon2Library) Native.loadLibrary("argon2", Argon2Library.class);

    int ARGON2_OK = 0;

    /*
    int argon2i_hash_encoded(const uint32_t t_cost, const uint32_t m_cost,
                         const uint32_t parallelism, const void *pwd,
                         const size_t pwdlen, const void *salt,
                         const size_t saltlen, const size_t hashlen,
                         char *encoded, const size_t encodedlen);
     */
    int argon2i_hash_encoded(Uint32_t t_cost, Uint32_t m_cost, Uint32_t parallelism, byte[] pwd, Size_t pwdlen, byte[] salt, Size_t saltlen, Size_t hashlen, byte[] encoded, Size_t encodedlen);

    /*
    int argon2i_verify(const char *encoded, const void *pwd, const size_t pwdlen);
     */
    int argon2i_verify(byte[] encoded, byte[] pwd, Size_t pwdlen);
}
