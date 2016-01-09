# Argon2 Bindings for the JVM

This is a JVM binding for [Argon2](https://github.com/P-H-C/phc-winner-argon2).

## Maven
```
TODO
```
## Gradle
```
TODO
```
### Usage
```
// Create instance
Argon2 argon2 = Argon2Factory.create();

// Hash password
String hash = argon2.hash(2, 65536, 1, "password");

// Verify password
if (argon2.verify(hash, "password")) {
    // Hash matches password
} else {
    // Hash doesn't match password
}
```

### Technical details
This library uses [JNA](https://github.com/java-native-access/jna) to communicate with the Argon2 C library.

### License
[LGPL v3](https://www.gnu.org/licenses/lgpl.html)

### Maintainer
[@phXql](https://github.com/phxql)