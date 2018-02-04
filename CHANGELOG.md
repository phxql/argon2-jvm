# Change Log
All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](http://semver.org/).

## Unreleased

### Added

- Added `Automatic-Module-Name` to JAR files
- Added support for Linux ARM. Compiled on a Raspberry Pi 2.
- Added support for Linux ARM-64. Compiled on a Raspberry Pi 3.

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
- Recompiled Argon2 libraries (Argon2 version is now 20161029).

## [2.1] - 2016-10-31
### Added
- Added artifact without the pre-compiled argon2 libraries.

### Changed
- The libraries are now uploaded to Maven Central instead to Bintray.

## [2.0] - 2016-10-03
*Attention*: This release sets the default charset to UTF-8 instead of using the system default. If your system default
haven't been UTF-8, refactor your code to use the overloads which accept `Charset`.

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
