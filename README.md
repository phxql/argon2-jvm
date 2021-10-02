# Argon2 Binding for the JVM ![Build & test](https://github.com/phxql/argon2-jvm/workflows/Build%20&%20test/badge.svg)

This is a JVM binding for [Argon2](https://github.com/P-H-C/phc-winner-argon2).

"Being a binding" means that it uses JNA to call a native C library. The advantage of this approach is that this uses the original implementation of the Argon2 authors and possibly a performance advantage. The drawback is that the native library must either be available on your system or must be included in argon2-jvm.

If you are using an OS or an architecture which is not that common (e.g. argon2-jvm has no support for BSD), this library will not work for you, if your system has no native installation of the Argon2 library.

[Spring Security Crypto](https://github.com/spring-projects/spring-security/tree/master/crypto) has a `Argon2PasswordEncoder`
which doesn't have any dependencies on native C libraries, as it uses Bouncy Castle which implements the Argon2 algorithm in pure Java. And don't let the name "Spring Security Crypto" scare you, it has no dependency on Spring or Spring Security. If you don't need (for whatever reason) the native Argon2 library, I would suggest that you look at the `Argon2PasswordEncoder` from Spring Security Crypto.

I have prepared a [small showcase project](https://github.com/phxql/argon2-playground) which demonstrates the use of Argon2 with Spring Security Crypto.

## Maven

Without pre-compiled Argon2 libraries (recommended, install argon2 via your package manager):

```xml
<dependency>
    <groupId>de.mkammerer</groupId>
    <artifactId>argon2-jvm-nolibs</artifactId>
    <version>2.11</version>
</dependency>
```

With pre-compiled Argon2 libraries:

```xml
<dependency>
    <groupId>de.mkammerer</groupId>
    <artifactId>argon2-jvm</artifactId>
    <version>2.11</version>
</dependency>
```

## Gradle

Without pre-compiled Argon2 libraries (recommended, install argon2 via your package manager):

```groovy
implementation 'de.mkammerer:argon2-jvm-nolibs:2.11'
```

With pre-compiled Argon2 libraries:

```groovy
implementation 'de.mkammerer:argon2-jvm:2.11'
```

## Usage

This binding needs a compiled Argon2 library. It is recommended to install argon2 via your package manager. If you can't do that, use `argon2-jvm` with the included argon2 binary libraries or compile argon2 yourself. The following operating systems and architectures are supported in `argon2-jvm`:

* Linux x86
* Linux x86-64
* Linux ARM
* Linux ARM-64
* Windows x86
* Windows x86-64
* Darwin (OSX)
* Darwin (OSX) M1

See [tested distributions](compatibility-tests/README.md) for details on which distributions this has been tested.

```java
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

// Create instance
Argon2 argon2 = Argon2Factory.create();

// Read password from user
char[] password = readPasswordFromUser();

try {
    // Hash password
    String hash = argon2.hash(10, 65536, 1, password);

    // Verify password
    if (argon2.verify(hash, password)) {
        // Hash matches password
    } else {
        // Hash doesn't match password
    }
} finally {
    // Wipe confidential data
    argon2.wipeArray(password);
}
```

## Recommended parameters

The recommended parameters for the `hash` call above can be found in the [whitepaper](https://github.com/P-H-C/phc-winner-argon2/blob/master/argon2-specs.pdf), section 9.

You can use the method `Argon2Helper.findIterations` to find the optimal number of iterations on your system:

```java
Argon2 argon2 = Argon2Factory.create();
// 1000 = The hash call must take at most 1000 ms
// 65536 = Memory cost
// 1 = parallelism
int iterations = Argon2Helper.findIterations(argon2, 1000, 65536, 1);

System.out.println("Optimal number of iterations: " + iterations);
```

## Compile Argon2 yourself

If you prefer to install/compile argon2 on your own you should `argon2-jvm-nolibs` instead of `argon2-jvm` and compile argon2 yourself. It's not that hard :)

If you need help to build argon2, have a look at [this documentation](docs/compile-argon2.md).

## Technical details

This library uses [JNA](https://github.com/java-native-access/jna) to communicate with the Argon2 C library.

## Building it yourself

Run `./gradlew clean build` to build and test the software.

## License

Licensed under [LGPL v3](https://www.gnu.org/licenses/lgpl.html).

## Maintainer

Moritz Kammerer ([@phXql](https://github.com/phxql))

## Contributing

See [contributing guidelines](CONTRIBUTING.md).

## Contributors

See [contributors page](https://github.com/phxql/argon2-jvm/graphs/contributors).
