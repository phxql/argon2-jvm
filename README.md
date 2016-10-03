# Argon2 Binding for the JVM [![Build Status](https://travis-ci.org/phxql/argon2-jvm.svg?branch=master)](https://travis-ci.org/phxql/argon2-jvm)

This is a JVM binding for [Argon2](https://github.com/P-H-C/phc-winner-argon2).

## Maven
Add this repository:
```
<repositories>
  <repository>
    <snapshots>
      <enabled>false</enabled>
    </snapshots>
    <id>bintray-phxql-maven</id>
    <name>bintray</name>
    <url>https://dl.bintray.com/phxql/maven</url>
  </repository>
</repositories>
```

You can then use the following Maven coordinates:
```
<dependency>
    <groupId>de.mkammerer</groupId>
    <artifactId>argon2-jvm</artifactId>
    <version>2.0</version>
</dependency>
```
## Gradle
```
repositories {
    maven {
        url  "https://dl.bintray.com/phxql/maven"
    }
}

dependencies {
    compile 'de.mkammerer:argon2-jvm:2.0'
}
```
## Usage
This binding needs the Argon2 C library. Libraries for the following operation systems are included:
* Linux x86
* Linux x86-64
* Windows x86
* Windows x86-64
* Darwin (OSX)

If your operating system isn't in the list, have a look at [this documentation](docs/compile-argon2.md).

```
// Create instance
Argon2 argon2 = Argon2Factory.create();

// Read password from user
char[] password = readPasswordFromUser();

try {
    // Hash password
    String hash = argon2.hash(2, 65536, 1, password);

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

## Technical details
This library uses [JNA](https://github.com/java-native-access/jna) to communicate with the Argon2 C library.

## Building it yourself
Run `./gradlew clean build` to build and test the software.

## License
[LGPL v3](https://www.gnu.org/licenses/lgpl.html)

## Maintainer
Moritz Kammerer ([@phXql](https://github.com/phxql))

## Contributing
See [contributing.md](contributing.md)

## Contributor
See [contributor.md](contributor.md)