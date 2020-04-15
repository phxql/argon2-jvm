# Change Log
All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](http://semver.org/).

## Unreleased

## 2.7 - 2020-04-15

### Added
- Added method `needsRehash` to check if a hash needs to be upgraded
- Updated JNA to 5.5.0
- Recompiled Argon2 for Linux x86 / x64 / ARM / ARM 64 on Ubuntu 16.04 

## [2.6] - 2019-10-06

### Added
- Added methods `hash` and `rawHash` for byte arrays
- Added method `pbkdf` to ease generating key material from a password

### Changed
- Marked all methods accepting a `String` password as deprecated. These methods will be removed in version 3
- Update Argon2 libraries to 20190702
- Update JNA to 5.4.0

## [2.5] - 2018-10-07

### Added
- Added method in `Argon2Advanced` which takes a pre-generated salt. See [#45](https://github.com/phxql/argon2-jvm/issues/45)
- Added warmup runs in the `Argon2Helper.findIterations` method to reduce JIT-related timing issues

### Changed
- Updated Argon2 to version [20171227](https://github.com/P-H-C/phc-winner-argon2/releases/tag/20171227)
- Updated JNA from 4.5.0 to 4.5.2

## [2.4] - 2018-02-24

### Added
- Added `Automatic-Module-Name` to JAR files
- Added support for Linux ARM. Compiled on a Raspberry Pi 2
- Added support for Linux ARM-64. Compiled on a Raspberry Pi 3
- Added `Argon2Helper` class with a method to find the optimal number of iterations

### Fixed
- Fixed wrong Argon2 id mapping ([PR](https://github.com/phxql/argon2-jvm/pull/41))

## [2.3] - 2017-12-12

### Added
- Added support for Argon2 raw hashes

### Changed
- Updated version from JNA from 4.2.1 to 4.5.0

## [2.2] - 2017-01-29

### Added
- Added support for Argon2id.

### Fixed
- Fixed [#22](https://github.com/phxql/argon2-jvm/issues/22).

### Changed
- Recompiled Argon2 libraries (Argon2 version is now 20161029)

## [2.1] - 2016-10-31
### Added
- Added artifact without the pre-compiled argon2 libraries

### Changed
- The libraries are now uploaded to Maven Central instead to Bintray

## [2.0] - 2016-10-03
*Attention*: This release sets the default charset to UTF-8 instead of using the system default. If your system default
haven't been UTF-8, refactor your code to use the overloads which accept `Charset`

### Added
- Specified the release from which the binaries have been built (see README.md in the `resources` folder). See #7
- Added overloads to hash and verify methods which accept `char[]` for password. See #9
- Added overloads to hash and verify methods which accept a `Charset`. See #10

### Changed
- Recompiled libraries for Linux x86 and x64 and for Windows x86 and x64
- The internal arrays which contain the passwords are now wiped after hash creation / verification. See #9
- Changed the default charset from JVM system default to UTF-8. See #10

## [1.2] - 2016-04-28
### Added
- Added support for Argon2d
- Added configurable salt and hash lengths

### Changed
- Better memory management
- Better nul byte handling
- Updated Argon2 library for Linux-x86 and Linux-x86-64
- Updated Argon2 library for Windows-x86 and Windows-x86-64
- Removed pre-compiled libraries from source JAR

## [1.1.0] - 2016-01-21
### Added
- Argon2 library for Linux-x86, Windows-x86, Windows-x64, Darwin
- Docs to describe how to compile Argon2 yourself
- Changelog

## [1.0.0] - 2016-01-10
### Added
- Initial version
