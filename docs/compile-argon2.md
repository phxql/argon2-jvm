# How to compile Argon2 yourself

1. Clone Argon2 source: `git clone git@github.com:P-H-C/phc-winner-argon2.git`
2. `cd phc-winner-argon2.git`
3. Compile and test it: `make && make test`
4. Copy the library (name usually starts with `libargon2`) to a place of your choice
5. Set the Java system property `jna.library.path` to the directory where your library is stored. Example: The library is located in `/home/moe/tmp/phc-winner-argon2/libargon2.so`. Start your Java application with `-Djna.library.path=/home/moe/tmp/phc-winner-argon2/`
6. If it doesn't work, set the Java system property `jna.debug_load` to `true`. This will print JNA library lookup details to the console. Example: `-Djna.debug_load=true`

You can make it available to JNA by placing them under your `resources/{OS}_{ARCH}`. In this case you don't need to use any extra system property.
Check the [JNA getting started guide](https://github.com/java-native-access/jna/blob/master/www/GettingStarted.md) for details.

## Install it on your local system

1. `make install`
1. Now Argon2-JVM should find it and use it. No need to set additional properties
