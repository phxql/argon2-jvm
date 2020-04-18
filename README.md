# Argon2 Binding for the JVM [![Build Status](https://travis-ci.org/phxql/argon2-jvm.svg?branch=master)](https://travis-ci.org/phxql/argon2-jvm)

This is a JVM binding for [Argon2](https://github.com/P-H-C/phc-winner-argon2).

## Maven
With pre-compiled Argon2 libraries:

```xml
<dependency>
    <groupId>de.mkammerer</groupId>
    <artifactId>argon2-jvm</artifactId>
    <version>2.8-SNAPSHOT</version>
</dependency>
```

Without pre-compiled Argon2 libraries:

```xml
<dependency>
    <groupId>de.mkammerer</groupId>
    <artifactId>argon2-jvm-nolibs</artifactId>
    <version>2.8-SNAPSHOT</version>
</dependency>
```

## Gradle
With pre-compiled Argon2 libraries:

```groovy
implementation 'de.mkammerer:argon2-jvm:2.8-SNAPSHOT'
```

Without pre-compiled Argon2 libraries:

```groovy
implementation 'de.mkammerer:argon2-jvm-nolibs:2.8-SNAPSHOT'
```

## Usage
This binding needs the Argon2 C library. Libraries for the following operation systems are included in argon2-jvm library:
* Linux x86
* Linux x86-64
* Linux ARM
* Linux ARM-64
* Windows x86
* Windows x86-64
* Darwin (OSX)

See [tested distributions](compatibility-tests/README.md) for details on which distributions this has been tested.

If you'd prefer to install/compile argon2 on your own you can use argon2-jvm-nolibs instead of argon2-jvm.

If you need help to build argon2, have a look at [this documentation](docs/compile-argon2.md).

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
