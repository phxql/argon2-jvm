This directory stores the compiled versions of the Argon2 C library.

The subfolders must have the name of the platform, for which the library has been compiled. See [the JNA documentation](https://java-native-access.github.io/jna/4.5.0/javadoc/com/sun/jna/NativeLibrary.html) for details. You can use [this tool](https://github.com/phxql/jna-info) to retrieve the platform name.

The libraries are compiled from [this source code](https://github.com/P-H-C/phc-winner-argon2/releases/tag/20190702).

## Linux:
All binaries have been compiled on Ubuntu 16.04 against `libc-2.23.so`.

* x64: `make clean && CFLAGS=-m64 OPTTARGET=generic make`
* x86: `make clean && CFLAGS=-m32 OPTTARGET=generic make`
* arm: `make clean && make`
* arm-64: `make clean && make`

See [this script](../../../libargon2/context/build-libargon2.sh) for details.

## Windows
Compiled with Visual Studio 2019, `ReleaseStatic` configuration

## OSX:
* `make clean && OPTTARGET=generic make`
